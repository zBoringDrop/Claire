package it.unict.davidemilazzo.claire.initializer;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.model.AIProviderEntity;
import it.unict.davidemilazzo.claire.model.AIType;
import it.unict.davidemilazzo.claire.respository.AIProviderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AIProviderInitializer implements CommandLineRunner {

    private final AIProviderRepository aiProviderRepository;

    @Value("${ollama.provider.url}")
    private String ollamaUrl;

    @Value("${lmstudio.provider.url}")
    private String lmStudioUrl;

    @Value("${localai.provider.url}")
    private String localAiUrl;

    private static final Logger log = LogManager.getLogger(AIProviderInitializer.class);

    @Override
    public void run(String... args) {

        HashSet<AIProviderEntity> aiProviderEntities = new HashSet<>(List.of(
                new AIProviderEntity(null, AIProvidersEnum.OPENAI, "https://api.openai.com", AIType.CLOUD,
                        "The industry standard for LLMs, offering flagship models like GPT-4o and o1 with advanced reasoning and multimodal capabilities."),
                new AIProviderEntity(null, AIProvidersEnum.ANTHROPIC, "https://api.anthropic.com", AIType.CLOUD,
                        "Focuses on safety and high steerability with the Claude 3.5 model family, offering massive context windows and superior coding abilities."),
                new AIProviderEntity(null, AIProvidersEnum.GOOGLE_AI, "https://generativelanguage.googleapis.com/v1beta", AIType.CLOUD,
                        "Provides access to Gemini Pro and Flash models, featuring native multimodality and deep integration with the Google ecosystem."),
                new AIProviderEntity(null, AIProvidersEnum.MICROSOFT_AZURE_AI, "https://<resource>.openai.azure.com/openai", AIType.CLOUD,
                        "Enterprise-grade implementation of OpenAI models with enhanced security, private networking, compliance certifications, and regional SLAs."),
                new AIProviderEntity(null, AIProvidersEnum.IBM_WATSON, "https://api.us-south.assistant.watson.cloud.ibm.com", AIType.CLOUD,
                        "Business-oriented platform (watsonx.ai) focusing on governance, data lineage, and the Granite model family for enterprise domains."),

                new AIProviderEntity(null, AIProvidersEnum.HUGGING_FACE, "https://api-inference.huggingface.co", AIType.CLOUD,
                        "The central hub for open-source AI, offering serverless Inference APIs to run thousands of community models (Llama, BERT, Flux) on demand."),
                new AIProviderEntity(null, AIProvidersEnum.XAI_GROK, "https://api.x.ai", AIType.CLOUD,
                        "Offers the Grok model series, distinguished by its direct real-time access to the X (Twitter) data stream for current events analysis."),
                new AIProviderEntity(null, AIProvidersEnum.COHERE, "https://api.cohere.ai", AIType.CLOUD,
                        "Specializes in enterprise GenAI with a strong focus on RAG (Retrieval-Augmented Generation), Command R+ models, and high-performance embeddings."),
                new AIProviderEntity(null, AIProvidersEnum.MISTRAL_AI, "https://api.mistral.ai", AIType.CLOUD,
                        "European lab known for efficient 'Mixture-of-Experts' (MoE) architectures, offering both open-weights (Mixtral) and proprietary flagship models."),
                new AIProviderEntity(null, AIProvidersEnum.AWS_BEDROCK, "https://bedrock-runtime.us-east-1.amazonaws.com", AIType.CLOUD,
                        "A fully managed service offering a unified API to access foundation models from Amazon (Titan), AI21, Anthropic, Cohere, and Meta."),
                new AIProviderEntity(null, AIProvidersEnum.PERPLEXITY_AI, "https://api.perplexity.ai", AIType.CLOUD,
                        "Provides 'online' LLM APIs (Sonar series) capable of browsing the internet to generate up-to-date responses with citations."),
                new AIProviderEntity(null, AIProvidersEnum.REPLICATE, "https://api.replicate.com", AIType.CLOUD,
                        "Scalable platform for running diverse open-source models via API, particularly popular for image generation and fine-tuned LLMs."),

                new AIProviderEntity(null, AIProvidersEnum.OLLAMA, ollamaUrl, AIType.LOCAL,
                        "A lightweight framework for running LLMs locally, simplifying the setup of models like Llama 3 and Phi-3 with an OpenAI-compatible API."),
                new AIProviderEntity(null, AIProvidersEnum.LM_STUDIO, lmStudioUrl, AIType.LOCAL,
                        "A desktop GUI designed for discovering and running quantized local models (GGUF format) with an integrated local inference server."),
                new AIProviderEntity(null, AIProvidersEnum.LOCAL_AI, localAiUrl, AIType.LOCAL,
                        "A self-hosted, drop-in replacement REST API for OpenAI, supporting CPU/GPU inference for a wide range of model architectures.")
        ));

        log.info("Updating 'ai_provider' table with default values...");
        for (AIProviderEntity aiProviderEntity : aiProviderEntities) {
            if (aiProviderRepository.findByName(aiProviderEntity.getName()) == null) {
                aiProviderRepository.save(aiProviderEntity);
            }
        }
        log.info("Table 'ai_provider' updated successfully");
    }
}

