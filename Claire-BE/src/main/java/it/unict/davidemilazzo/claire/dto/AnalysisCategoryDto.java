package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryEntity;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;
import lombok.*;

@Data
@NoArgsConstructor
public class AnalysisCategoryDto {
    private Long id;
    private AnalysisCategoryType type;
    private String description;
    private String prompt;
    private JsonNode jsonSchema;

    @JsonCreator
    public AnalysisCategoryDto(@JsonProperty("id") Long id,
                              @JsonProperty("type") AnalysisCategoryType type,
                              @JsonProperty("description") String description,
                              @JsonProperty("prompt") String prompt,
                              @JsonProperty("json_schema") JsonNode jsonSchema) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.prompt = prompt;
        this.jsonSchema = jsonSchema;
    }
}