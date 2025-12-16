package it.unict.davidemilazzo.claire.ai.ollama;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaModelInfoDto {

    @JsonProperty("model")
    private String model;

    @JsonProperty("name")
    private String name;

    @JsonProperty("modified_at")
    private String modifiedAt;

    @JsonProperty("size")
    private long size;

    @JsonProperty("digest")
    private String digest;

    @JsonProperty("details")
    private OllamaModelDetailsDto details;
}
