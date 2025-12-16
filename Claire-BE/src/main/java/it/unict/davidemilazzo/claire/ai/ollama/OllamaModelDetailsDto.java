package it.unict.davidemilazzo.claire.ai.ollama;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaModelDetailsDto {

    @JsonProperty("parent_model")
    private String parentModel;

    @JsonProperty("format")
    private String format;

    @JsonProperty("family")
    private String family;

    @JsonProperty("families")
    private List<String> families;

    @JsonProperty("parameter_size")
    private String parameterSize;

    @JsonProperty("quantization_level")
    private String quantizationLevel;
}

