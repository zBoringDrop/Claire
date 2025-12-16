package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.model.AIType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProviderApiKeyPreviewDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("provider_id")
    private Long providerId;

    @JsonProperty("provider_name")
    private AIProvidersEnum providerName;

    @JsonProperty("provider_type")
    private AIType aiType;

    @JsonProperty("base_url")
    private String providerBaseUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("active")
    private Boolean active;
}

