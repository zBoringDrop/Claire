package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CodeSnippetPreviewDto {
    private Long id;
    private Long userId;
    private String title;
    private LocalDateTime creationDatetime;
    private String language;
    private String codeText;
    private Long analysisCount;
    private Boolean deleted;

    @JsonCreator
    public CodeSnippetPreviewDto(@JsonProperty("id") Long id,
                                 @JsonProperty("user_id") Long userId,
                                 @JsonProperty("title") String title,
                                 @JsonProperty("creation_datetime") LocalDateTime creationDatetime,
                                 @JsonProperty("language") String language,
                                 @JsonProperty("code_text") String codeText,
                                 @JsonProperty("analysis_count") Long analysisCount,
                                 @JsonProperty("deleted") Boolean deleted) {

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.creationDatetime = creationDatetime;
        this.language = language;
        this.codeText = codeText;
        this.analysisCount = analysisCount;
        this.deleted = deleted;
    }
}
