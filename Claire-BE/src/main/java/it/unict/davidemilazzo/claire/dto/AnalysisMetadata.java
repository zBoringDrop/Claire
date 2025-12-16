package it.unict.davidemilazzo.claire.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnalysisMetadata {
    private Byte overallSeverity;
    private Integer outputLength;
    private Integer issuesCount;
}
