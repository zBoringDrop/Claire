package it.unict.davidemilazzo.claire.ai.ollama;

import it.unict.davidemilazzo.claire.ai.AiProviderConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OllamaSpecificConfig implements AiProviderConfig {
    private UserOllamaCustomConfigDto customConfigDto;
}
