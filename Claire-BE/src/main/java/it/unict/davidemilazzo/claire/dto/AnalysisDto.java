package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import it.unict.davidemilazzo.claire.model.AnalysisStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class AnalysisDto {
    private Long id;
    private AnalysisStatus status;
    private String message;
    private Long fileId;
    private Long codeSnippetId;
    private Long toolId;
    private List<Long> analysisCategoryIds;
    private LocalDateTime endDatetime;
    private Long executionMs;
    private Byte overallSeverity;
    private Integer outputLength;
    private Integer issuesCount;
    private JsonNode resultJson;
    private LocalDateTime createdAt;

    @JsonCreator
    public AnalysisDto(@JsonProperty("id") Long id,
                       @JsonProperty("status") AnalysisStatus status,
                       @JsonProperty("message") String message,
                       @JsonProperty("file_id") Long fileId,
                       @JsonProperty("codesnippet_id") Long codeSnippetId,
                       @JsonProperty("tool_id") Long toolId,
                       @JsonProperty("analysis_categoryIds") List<Long> analysisCategoryIds,
                       @JsonProperty("end_datetime") LocalDateTime endDatetime,
                       @JsonProperty("execution_ms") Long executionMs,
                       @JsonProperty("overall_severity") Byte overallSeverity,
                       @JsonProperty("output_length") Integer outputLength,
                       @JsonProperty("issues_count") Integer issuesCount,
                       @JsonProperty("result_json") JsonNode resultJson,
                       @JsonProperty("created_at") LocalDateTime createdAt) {

        this.id = id;
        this.status = status;
        this.message = message;
        this.fileId = fileId;
        this.codeSnippetId = codeSnippetId;
        this.toolId = toolId;
        this.analysisCategoryIds = analysisCategoryIds;
        this.resultJson = resultJson;
        this.endDatetime = endDatetime;
        this.executionMs = executionMs;
        this.overallSeverity = overallSeverity;
        this.outputLength = outputLength;
        this.issuesCount = issuesCount;
        this.createdAt = createdAt;
    }
}
