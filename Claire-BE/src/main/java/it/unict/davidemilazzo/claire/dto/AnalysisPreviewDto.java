package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import it.unict.davidemilazzo.claire.model.AnalysisStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnalysisPreviewDto {
    private Long id;
    private AnalysisStatus status;
    private String message;
    private Long fileId;
    private Long codeSnippetId;
    private String sourceName;
    private String sourcePreview;
    private Boolean sourceDeleted;
    private String toolName;
    private Byte overallSeverity;
    private Integer issuesCount;
    private LocalDateTime createdAt;

    @JsonCreator
    public AnalysisPreviewDto(@JsonProperty("id") Long id,
                               @JsonProperty("status") AnalysisStatus status,
                               @JsonProperty("message") String message,
                               @JsonProperty("file_id") Long fileId,
                               @JsonProperty("codesnippet_id") Long codeSnippetId,
                               @JsonProperty("source_name") String sourceName,
                               @JsonProperty("source_preview") String sourcePreview,
                               @JsonProperty("source_deleted") Boolean sourceDeleted,
                               @JsonProperty("tool_name") String toolName,
                               @JsonProperty("overall_severity") Byte overallSeverity,
                               @JsonProperty("issues_count") Integer issuesCount,
                               @JsonProperty("created_at") LocalDateTime createdAt) {

        this.id = id;
        this.status = status;
        this.message = message;
        this.fileId = fileId;
        this.codeSnippetId = codeSnippetId;
        this.sourceName = sourceName;
        this.sourcePreview = sourcePreview;
        this.sourceDeleted = sourceDeleted;
        this.toolName = toolName;
        this.overallSeverity = overallSeverity;
        this.issuesCount = issuesCount;
        this.createdAt = createdAt;
    }
}
