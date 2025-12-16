package it.unict.davidemilazzo.claire.ai;

import it.unict.davidemilazzo.claire.model.AIType;

import java.util.List;


public enum AIProvidersEnum {

    OPENAI(AIType.CLOUD),
    ANTHROPIC(AIType.CLOUD),
    GOOGLE_AI(AIType.CLOUD),
    MICROSOFT_AZURE_AI(AIType.CLOUD),
    IBM_WATSON(AIType.CLOUD),
    HUGGING_FACE(AIType.CLOUD),
    XAI_GROK(AIType.CLOUD),
    COHERE(AIType.CLOUD),
    MISTRAL_AI(AIType.CLOUD),
    AWS_BEDROCK(AIType.CLOUD),
    PERPLEXITY_AI(AIType.CLOUD),
    REPLICATE(AIType.CLOUD),

    OLLAMA(AIType.LOCAL),
    LM_STUDIO(AIType.LOCAL),
    LOCAL_AI(AIType.LOCAL);

    private final AIType type;

    AIProvidersEnum(AIType type) {
        this.type = type;
    }

    public AIType getType() {
        return type;
    }

    public boolean isLocal() {
        return AIType.LOCAL.equals(this.type);
    }

    public boolean isCloud() {
        return AIType.CLOUD.equals(this.type);
    }

    public static List<AIProvidersEnum> list() {
        return List.of(AIProvidersEnum.values());
    }
}
