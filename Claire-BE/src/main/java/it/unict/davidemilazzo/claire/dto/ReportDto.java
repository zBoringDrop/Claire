package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.FormatType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReportDto {
    private Long id;
    private Long analysisId;
    private FormatType formatType;
    private String path;
    private LocalDateTime createdAt;

    @JsonCreator
    public ReportDto(@JsonProperty("id") Long id,
                     @JsonProperty("analysis_id") Long analysisId,
                     @JsonProperty("format_type") FormatType formatType,
                     @JsonProperty("path") String path,
                     @JsonProperty("created_at") LocalDateTime createdAt) {

        this.id = id;
        this.analysisId = analysisId;
        this.formatType = formatType;
        this.path = path;
        this.createdAt = createdAt;
    }
}
