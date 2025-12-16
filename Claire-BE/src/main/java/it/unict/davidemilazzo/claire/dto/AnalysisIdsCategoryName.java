package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisIdsCategoryName {
    @JsonProperty("analysis_id")
    private Long analysisId;

    @JsonProperty("category_name")
    private String categoryName;
}
