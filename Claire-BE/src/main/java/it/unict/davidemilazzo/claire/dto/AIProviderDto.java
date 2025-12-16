package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.model.AIType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AIProviderDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private AIProvidersEnum name;

    @JsonProperty("base_url")
    private String baseUrl;

    @JsonProperty("type")
    private AIType aiType;

    @JsonProperty("description")
    private String description;
}
