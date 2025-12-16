package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CodeSnippetDto {
    private Long id;
    private Long userId;
    private String title;
    private LocalDateTime creationDatetime;
    private Long programmingLanguageId;
    private String codeText;
    private Boolean deleted;

    @JsonCreator
    public CodeSnippetDto(@JsonProperty("id") Long id,
                          @JsonProperty("user_id") Long userId,
                          @JsonProperty("title") String title,
                          @JsonProperty("creation_datetime") LocalDateTime creationDatetime,
                          @JsonProperty("programming_language_id") Long programmingLanguageId,
                          @JsonProperty("code_text") String codeText,
                          @JsonProperty("deleted") Boolean deleted) {

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.creationDatetime = creationDatetime;
        this.programmingLanguageId = programmingLanguageId;
        this.codeText = codeText;
        this.deleted = deleted;
    }
}
