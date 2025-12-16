package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammingLanguageDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
