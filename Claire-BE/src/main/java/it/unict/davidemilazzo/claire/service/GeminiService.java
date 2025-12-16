package it.unict.davidemilazzo.claire.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.ai.AnalysisConfig;
import it.unict.davidemilazzo.claire.ai.gemini.GoogleModelInfoDto;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.JsonParseErrorException;
import it.unict.davidemilazzo.claire.exception.ModelNotFoundException;
import it.unict.davidemilazzo.claire.exception.ModelSyncException;
import it.unict.davidemilazzo.claire.model.AIType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeminiService implements ModelSyncInt {

    private final AIService aiService;
    private final AIProviderService aiProviderService;
    private static final Logger log = LogManager.getLogger(GeminiService.class);

    @Override
    public AIProvidersEnum provider() {
        return AIProvidersEnum.GOOGLE_AI;
    }

    private List<GoogleModelInfoDto> getGoogleCloudModel(String baseUrl, String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        List<GoogleModelInfoDto> allModels = new ArrayList<>();
        String pageToken = null;

        try {
            do {
                String url = baseUrl + "?key=" + apiKey;

                if (pageToken != null) {
                    url += "&pageToken=" + pageToken;
                }

                JsonNode root = restTemplate.getForObject(url, JsonNode.class);
                if (root == null || root.get("models") == null) {
                    break;
                }

                GoogleModelInfoDto[] models = mapper.treeToValue(root.get("models"), GoogleModelInfoDto[].class);

                for (GoogleModelInfoDto model : models) {
                    if (model.getSupportedGenerationMethods() != null &&
                            model.getSupportedGenerationMethods().contains("generateContent")) {
                        allModels.add(model);
                    }
                }

                JsonNode tokenNode = root.get("nextPageToken");
                pageToken = (tokenNode != null && !tokenNode.isNull()) ? tokenNode.asText() : null;

            } while (pageToken != null);

            //log.info("Found {} models supporting generateContent: {}", allModels.size(), allModels);
            return allModels;

        } catch (JsonProcessingException e) {
            log.error("Error parsing Google Models JSON", e);
            throw new ModelSyncException(ExceptionMessages.MODEL_SYNC_FAILED);
        } catch (Exception e) {
            log.error("Error fetching models from Google: {}", e.getMessage());
            throw new ModelSyncException(ExceptionMessages.MODEL_SYNC_FAILED);
        }
    }

    @Override
    @Transactional
    public void syncModelsWithDatabase(final String apiKey) {

        final AIProviderDto aiProviderDto = aiProviderService.findByName(provider());

        List<GoogleModelInfoDto> models = getGoogleCloudModel(aiProviderDto.getBaseUrl() + "/models", apiKey);
        //log.info("Google cloud available models list: {}", models.toString());

        for (GoogleModelInfoDto model : models) {
            AIDto aiDto = null;
            String modelStr = model.getName().replace("models/", "");
            try {
                aiDto = AIDto.builder()
                        .id(null)
                        .name(model.getDisplayName())
                        .description(model.getDescription())
                        .model(modelStr)
                        .family("gemini")
                        .parameterSize(null)
                        .size(null)
                        .aiProviderId(aiProviderDto.getId())
                        .modifiedAt(LocalDateTime.now())
                        .lastDBSync(LocalDateTime.now())
                        .active(true)
                        .jsonExtra(new ObjectMapper().writeValueAsString(model))
                        .build();
            } catch (JsonProcessingException e) {
                throw new ModelSyncException(ExceptionMessages.MODEL_SYNC_FAILED);
            }

            if (!aiService.existsByModel(modelStr)) {
                aiService.createNew(aiDto);
            } else {
                AIDto aiDtoToUpdate = aiService.findByModel(modelStr);
                aiService.setActive(aiDtoToUpdate.getId(), true);
            }
        }
    }

}