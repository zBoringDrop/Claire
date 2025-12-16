package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FilePreviewDto {
    private Long id;
    private Long userId;
    private String filename;
    private String contentType;
    private LocalDateTime uploadDatetime;
    private String language;
    private long size;
    private String data;
    private Long analysisCount;
    private Boolean deleted;

    @JsonCreator
    public FilePreviewDto(@JsonProperty("id") Long id,
                          @JsonProperty("user_id") Long userId,
                          @JsonProperty("filename") String filename,
                          @JsonProperty("content_type") String contentType,
                          @JsonProperty("upload_datetime") LocalDateTime uploadDatetime,
                          @JsonProperty("language") String language,
                          @JsonProperty("size") long size,
                          @JsonProperty("data") String data,
                          @JsonProperty("analysis_count") Long analysisCount,
                          @JsonProperty("deleted") Boolean deleted) {

        this.id = id;
        this.userId = userId;
        this.filename = filename;
        this.contentType = contentType;
        this.uploadDatetime = uploadDatetime;
        this.language = language;
        this.size = size;
        this.data = data;
        this.analysisCount = analysisCount;
        this.deleted = deleted;
    }
}
