package it.unict.davidemilazzo.claire.ai;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.core.env.Environment;

@Configuration
public class AISpringConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Qualifier("ollamaChatModel")
    public OllamaChatModel ollamaChatModel() {

        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(environment.getProperty("ollama.base.url"))
                .build();

        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .build();
    }

    /*
    @Bean
    @Qualifier("gptChatModel")
    public OpenAiChatModel gptChatModel() {
        OpenAiApi api = OpenAiApi.builder()
                .apiKey(environment.getProperty("openai.api.key"))
                .baseUrl(environment.getProperty("openai.base.url"))
                .build();
        return OpenAiChatModel.builder()
                .openAiApi(api)
                .build();
    }
     */

    /*
    @Bean
    @Qualifier("anthropicChatModel")
    public AnthropicChatModel anthropicChatModel(
            @Value("${ANTHROPIC_API_KEY}") String apiKey) {
        AnthropicApi api = new AnthropicApi(apiKey);
        return new AnthropicChatModel(api)
                .withDefaultOptions(builder -> builder.temperature(0.1)
                        .model("claude-3-opus-20240229"));
    }
     */
}

