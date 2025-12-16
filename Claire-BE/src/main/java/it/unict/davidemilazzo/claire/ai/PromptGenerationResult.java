package it.unict.davidemilazzo.claire.ai;

import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;

public record PromptGenerationResult(AnalysisCategoryType analysisCategory, String prompt, String json) {
}
