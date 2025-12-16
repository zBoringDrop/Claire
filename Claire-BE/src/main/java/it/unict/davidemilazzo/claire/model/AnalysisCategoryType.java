package it.unict.davidemilazzo.claire.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnalysisCategoryType {
    SECURITY("Security"),
    RELIABILITY_AND_BUGS("Reliability and bugs"),
    PERFORMANCE("Performance"),
    ARCHITECTURE_AND_DESIGN("Architecture and design"),
    CODE_STRUCTURE_AND_COMPLEXITY("Code structure and complexity"),
    NAMING_AND_DOCUMENTATION("Naming and documentation");

    private final String displayName;
}
