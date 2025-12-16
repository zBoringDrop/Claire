package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.AIType;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AIDto extends ToolDto {
    private String model;
    private String family;
    private String parameterSize;
    private Long size;
    private Long aiProviderId;
    private String description;
    private boolean active;
    private LocalDateTime modifiedAt;
    private LocalDateTime lastDBSync;
    private String jsonExtra;

    @JsonCreator
    public AIDto(@JsonProperty("model") String model,
                 @JsonProperty("family") String family,
                 @JsonProperty("parameter_size") String parameterSize,
                 @JsonProperty("size") long size,
                 @JsonProperty("ai_provider_id") Long aiProviderId,
                 @JsonProperty("description") String description,
                 @JsonProperty("is_active") boolean active,
                 @JsonProperty("modified_at") LocalDateTime modifiedAt,
                 @JsonProperty("last_db_sync") LocalDateTime lastDBSync,
                 @JsonProperty("json_extra") String jsonExtra) {

        this.model = model;
        this.family = family;
        this.parameterSize = parameterSize;
        this.size = size;
        this.aiProviderId = aiProviderId;
        this.description = description;
        this.active = active;
        this.modifiedAt = modifiedAt;
        this.lastDBSync = lastDBSync;
        this.jsonExtra = jsonExtra;
    }
}
