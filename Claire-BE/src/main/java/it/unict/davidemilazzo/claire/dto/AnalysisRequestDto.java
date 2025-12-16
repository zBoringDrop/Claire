package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AnalysisRequestDto {
    private Long sourceId; //codeSnippetId or fileId
    private Long toolId;
    private Set<Long> analysisCategoryIds;

    @JsonCreator
    public AnalysisRequestDto(@JsonProperty("source_id") Long sourceId,
                              @JsonProperty("tool_id") Long toolId,
                              @JsonProperty("analysis_categories") Set<Long> analysisCategoryIds) {

        this.sourceId = sourceId;
        this.toolId = toolId;
        this.analysisCategoryIds = analysisCategoryIds;
    }
}
