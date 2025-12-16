package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProviderApiKeyDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("provider_id")
    private Long providerId;

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("active")
    private Boolean active;
}
