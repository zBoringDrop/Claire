package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.model.AIType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AIPreviewDto {
    @JsonProperty("ai_id")
    private Long aiId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("model")
    private String model;

    @JsonProperty("family")
    private String family;

    @JsonProperty("parameter_size")
    private String parameterSize;

    @JsonProperty("size")
    private Long size;

    @JsonProperty("provider_id")
    private Long aiProviderId;

    @JsonProperty("ai_description")
    private String description;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;

    @JsonProperty("last_db_sync")
    private LocalDateTime lastDBSync;

    @JsonProperty("ai_json_extra")
    private String jsonExtra;

    @JsonProperty("provider_name")
    private AIProvidersEnum providerName;

    @JsonProperty("base_url")
    private String baseUrl;

    @JsonProperty("provider_type")
    private AIType aiType;

    @JsonProperty("provider_description")
    private String providerDescription;
}
