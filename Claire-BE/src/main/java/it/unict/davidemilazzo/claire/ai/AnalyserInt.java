package it.unict.davidemilazzo.claire.ai;

import com.fasterxml.jackson.databind.JsonNode;

public interface AnalyserInt {
    JsonNode analyze(AnalysisConfig analysisConfig);
}
