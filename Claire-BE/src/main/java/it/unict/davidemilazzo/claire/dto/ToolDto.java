package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.ToolType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ToolDto {
    private Long id;
    private String name;
    private ToolType type;

    @JsonCreator
    public ToolDto(@JsonProperty("id") Long id,
                   @JsonProperty("name") String name,
                   @JsonProperty("type") ToolType type) {

        this.id = id;
        this.name = name;
        this.type = type;
    }
}