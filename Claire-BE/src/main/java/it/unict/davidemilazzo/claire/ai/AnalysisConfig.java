package it.unict.davidemilazzo.claire.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnalysisConfig {
    private String model;
    private String parameterSize;
    private String prompt;
    private String responseJson;
    private String category;

    private AiProviderConfig providerConfig;
}
