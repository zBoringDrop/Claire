package it.unict.davidemilazzo.claire.ai.gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleModelInfoDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private String version;

    @JsonProperty("inputTokenLimit")
    private Integer inputTokenLimit;

    @JsonProperty("outputTokenLimit")
    private Integer outputTokenLimit;

    @JsonProperty("supportedGenerationMethods")
    private List<String> supportedGenerationMethods;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("topP")
    private Double topP;

    @JsonProperty("topK")
    private Integer topK;

    @JsonProperty("thinking")
    private Boolean thinking;
}
