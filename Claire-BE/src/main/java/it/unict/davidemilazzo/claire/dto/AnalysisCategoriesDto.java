package it.unict.davidemilazzo.claire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnalysisCategoriesDto {
    private Long analysisId;
    private List<String> categories;
}
