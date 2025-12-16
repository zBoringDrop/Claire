package it.unict.davidemilazzo.claire.ai;

import it.unict.davidemilazzo.claire.ai.gemini.GeminiAnalyser;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto;
import it.unict.davidemilazzo.claire.model.AIType;
import it.unict.davidemilazzo.claire.service.AIProviderService;
import it.unict.davidemilazzo.claire.service.ApiKeyEncryptionService;
import it.unict.davidemilazzo.claire.service.OllamaService;
import it.unict.davidemilazzo.claire.service.UserProviderApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyserFactory {

    private final UserProviderApiKeyService userProviderApiKeyService;
    private final AIProviderService aiProviderService;
    private final ApiKeyEncryptionService apiKeyEncryptionService;
    private final OllamaService ollamaService;

    public AnalyserInt getAnalyser(Long providerId, Long userId) {

        AIProviderDto provider = aiProviderService.findById(providerId);

        if (provider.getAiType() == AIType.LOCAL) {
            return switch (provider.getName()) {
                case OLLAMA -> ollamaService;
                default -> throw new IllegalArgumentException("Unsupported local provider: " + provider.getName());
            };
        } else {
            UserProviderApiKeyPreviewDto userProviderApiKey =
                    userProviderApiKeyService.findPreviewByUserIdAndProviderId(userId, providerId);

            return switch (provider.getName()) {
                case GOOGLE_AI -> {
                    OpenAiApi api = OpenAiApi.builder()
                            .apiKey(apiKeyEncryptionService.decrypt(userProviderApiKey.getApiKey()))
                            .baseUrl(userProviderApiKey.getProviderBaseUrl() + "/openai/")
                            .build();

                    OpenAiChatModel model = OpenAiChatModel.builder()
                            .openAiApi(api)
                            .build();

                    yield new GeminiAnalyser(model);
                }
                default -> throw new IllegalArgumentException("Unsupported remote provider: " + provider.getName());
            };
        }


    }
}


