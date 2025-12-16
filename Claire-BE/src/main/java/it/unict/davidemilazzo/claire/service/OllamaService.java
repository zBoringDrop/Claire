package it.unict.davidemilazzo.claire.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.ai.AnalyserInt;
import it.unict.davidemilazzo.claire.ai.AnalysisConfig;
import it.unict.davidemilazzo.claire.ai.ollama.OllamaModelInfoDto;
import it.unict.davidemilazzo.claire.ai.ollama.OllamaConfigurations;
import it.unict.davidemilazzo.claire.ai.ollama.OllamaSpecificConfig;
import it.unict.davidemilazzo.claire.ai.ollama.UserOllamaCustomConfigDto;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.exception.*;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class OllamaService implements AnalyserInt, ModelSyncInt {

    private final ChatModel ollama;
    private final AIService aiService;
    private final AIProviderService aiProviderService;
    private final UserOllamaCustomConfigService userOllamaCustomConfigService;
    private static final Logger log = LogManager.getLogger(OllamaService.class);

    public OllamaService(@Qualifier("ollamaChatModel") ChatModel ollama,
                         AIService aiService,
                         AIProviderService aiProviderService,
                         UserOllamaCustomConfigService userOllamaCustomConfigService) {
        this.ollama = ollama;
        this.aiService = aiService;
        this.aiProviderService = aiProviderService;
        this.userOllamaCustomConfigService = userOllamaCustomConfigService;
    }

    @Override
    public AIProvidersEnum provider() {
        return AIProvidersEnum.OLLAMA;
    }

    private List<OllamaModelInfoDto> getModels(String url) {
        RestTemplate restTemplate = new RestTemplate();

        JsonNode root = restTemplate.getForObject(url, JsonNode.class);

        try {
            if (root == null)
                return new ArrayList<>();
            return Arrays.asList(
                    new ObjectMapper().treeToValue(root.get("models"), OllamaModelInfoDto[].class)
            );
        } catch (JsonProcessingException e) {
            throw new ModelSyncException(ExceptionMessages.MODEL_SYNC_FAILED);
        }
        //Es: ModelInfoDto(model=deepseek-r1:8b, name=deepseek-r1:8b, modifiedAt=2025-09-19T19:21:13.9842638+02:00, size=5225376047, digest=699587...763,
        // details=ModelDetailsDto(parentModel=, format=gguf, family=qwen3, families=[qwen3], parameterSize=8.2B, quantizationLevel=Q4_K_M))
    }

    @Transactional
    public void syncModelsWithDatabase(String apiKey) {
        final AIProviderDto aiProviderDto = aiProviderService.findByName(provider());

        List<OllamaModelInfoDto> models = getModels(aiProviderDto.getBaseUrl() + "/api/tags");
        //log.info("Ollama local installed models list: {}", models.toString());

        for (OllamaModelInfoDto model : models) {
            AIDto aiDto = null;
            try {
                aiDto = AIDto.builder()
                        .id(null)
                        .name(model.getName())
                        .model(model.getModel())
                        .family(model.getDetails().getFamily())
                        .parameterSize(model.getDetails().getParameterSize())
                        .size(model.getSize())
                        .aiProviderId(aiProviderDto.getId())
                        .modifiedAt(OffsetDateTime.parse(model.getModifiedAt()).toLocalDateTime())
                        .lastDBSync(LocalDateTime.now())
                        .active(true)
                        .jsonExtra(new ObjectMapper().writeValueAsString(model))
                        .build();
            } catch (JsonProcessingException e) {
                throw new ModelSyncException(ExceptionMessages.MODEL_SYNC_FAILED);
            }

            if (!aiService.existsByModel(model.getName())) {
                aiService.createNew(aiDto);
            } else {
                AIDto aiDtoToUpdate = aiService.findByName(model.getName());
                if (aiDtoToUpdate.getModifiedAt() == null || aiDto.getModifiedAt().isAfter(aiDtoToUpdate.getModifiedAt())) {
                    aiDto.setId(aiDtoToUpdate.getId());
                    aiService.update(aiDto);
                } else aiService.setActive(aiDtoToUpdate.getId(), true);
            }
        }
    }

    @Override
    public JsonNode analyze(AnalysisConfig analysisConfig) {
        UserOllamaCustomConfigDto customConfig = null;
        OllamaOptions ollamaOptions = null;

        try {

            if (analysisConfig.getProviderConfig() instanceof OllamaSpecificConfig) {
                customConfig = ((OllamaSpecificConfig) analysisConfig.getProviderConfig()).getCustomConfigDto();
            }

            if (customConfig != null) {

                ollamaOptions = OllamaConfigurations.fromDtoConfig(
                        analysisConfig.getModel(),
                        analysisConfig.getResponseJson(),
                        customConfig
                );
            }

            Prompt prompt = new Prompt(analysisConfig.getPrompt(), ollamaOptions);

            ChatResponse response = ollama.call(prompt);

            if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
                throw new RuntimeException("Ollama returned an empty response");
            }
            String text = response.getResult().getOutput().getText();
            JsonNode resultNode = new ObjectMapper().readTree(text);

            if (resultNode.has("error")) {
                throw new RuntimeException("Ollama Model Logic Error: " + resultNode.toPrettyString());
            }
            return resultNode;

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during Ollama analysis: " + e.getMessage(), e);
        }
    }

}
