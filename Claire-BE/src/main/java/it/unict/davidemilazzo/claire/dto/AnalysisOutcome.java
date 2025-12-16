package it.unict.davidemilazzo.claire.dto;

import it.unict.davidemilazzo.claire.model.AnalysisStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class AnalysisOutcome {
    private AnalysisStatus status;
    private String message;
}
