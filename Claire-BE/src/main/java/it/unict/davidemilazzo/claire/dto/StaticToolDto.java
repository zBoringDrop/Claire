package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StaticToolDto extends ToolDto {
    private String version;
    private Set<Long> supportedLanguageIds;
    private String executablePath;

    @JsonCreator
    public StaticToolDto(@JsonProperty("version") String version,
                         @JsonProperty("supported_languages_ids") Set<Long> supportedLanguageIds,
                         @JsonProperty("executable_path") String executablePath) {

        this.version = version;
        this.supportedLanguageIds = supportedLanguageIds;
        this.executablePath = executablePath;
    }
}
