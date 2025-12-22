package it.unict.davidemilazzo.claire.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.ai.SpecializedAnalysisPrompts;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryEntity;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;
import it.unict.davidemilazzo.claire.respository.AnalysisCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CategoryDataInitializer implements CommandLineRunner {

    private final AnalysisCategoryRepository analysisCategoryRepository;

    private final String EXAMPLE_CODE = "... Code to analyse ...";

    private static final Logger log = LogManager.getLogger(CategoryDataInitializer.class);

    private final Set<AnalysisCategoryEntity> categories = new HashSet<>(List.of(
            new AnalysisCategoryEntity(null, AnalysisCategoryType.SECURITY,
                    "Detects critical security vulnerabilities such as SQL injection, hardcoded credentials, missing input validation, weak cryptography, improper authentication/authorization, XSS/CSRF, and sensitive data exposure.",
                    SpecializedAnalysisPrompts.getSecurityPrompt(EXAMPLE_CODE, false),
                    new ObjectMapper().valueToTree(SpecializedAnalysisPrompts.getSecuritySchema(false)),
                    new HashSet<>()),

            new AnalysisCategoryEntity(null,
                    AnalysisCategoryType.RELIABILITY_AND_BUGS,
                    "Finds bugs and reliability issues like null pointer exceptions, resource or memory leaks, race conditions, logic errors, and unhandled exceptions that may cause application failures.",
                    SpecializedAnalysisPrompts.getReliabilityPrompt(EXAMPLE_CODE, false),
                    new ObjectMapper().valueToTree(SpecializedAnalysisPrompts.getReliabilitySchema(false)),
                    new HashSet<>()),

            new AnalysisCategoryEntity(null, AnalysisCategoryType.PERFORMANCE,
                    "Identifies performance inefficiencies such as slow algorithms, N+1 database queries, excessive memory allocations, blocking I/O, inefficient string operations, or missing caching.",
                    SpecializedAnalysisPrompts.getPerformancePrompt(EXAMPLE_CODE, false),
                    new ObjectMapper().valueToTree(SpecializedAnalysisPrompts.getPerformanceSchema(false)),
                    new HashSet<>()),

            new AnalysisCategoryEntity(null, AnalysisCategoryType.ARCHITECTURE_AND_DESIGN,
                    "Evaluates architectural and design quality, checking SOLID principle violations, high coupling, low cohesion, poor abstraction, misuse of design patterns, and mixed responsibilities.",
                    SpecializedAnalysisPrompts.getArchitecturePrompt(EXAMPLE_CODE, false),
                    new ObjectMapper().valueToTree(SpecializedAnalysisPrompts.getArchitectureSchema(false)),
                    new HashSet<>()),

            new AnalysisCategoryEntity(null, AnalysisCategoryType.CODE_STRUCTURE_AND_COMPLEXITY,
                    "Analyzes code complexity including deep nesting, long methods or classes, excessive parameters, code duplication, and dead code to improve readability and maintainability.",
                    SpecializedAnalysisPrompts.getStructurePrompt(EXAMPLE_CODE, false),
                    new ObjectMapper().valueToTree(SpecializedAnalysisPrompts.getStructureSchema(false)),
                    new HashSet<>()),

            new AnalysisCategoryEntity(null, AnalysisCategoryType.NAMING_AND_DOCUMENTATION,
                    "Reviews code readability by checking unclear or inconsistent naming, misleading identifiers, magic values, missing or outdated documentation, and style inconsistencies.",
                    SpecializedAnalysisPrompts.getNamingPrompt(EXAMPLE_CODE, false),
                    new ObjectMapper().valueToTree(SpecializedAnalysisPrompts.getNamingSchema(false)),
                    new HashSet<>())
    ));

    @Override
    public void run(String... args) throws Exception {
        log.info("Updating 'analysis_category' table with default values...");
        for (AnalysisCategoryEntity categoryEntity : categories) {
            if (!analysisCategoryRepository.existsByType(categoryEntity.getType()))
                analysisCategoryRepository.save(categoryEntity);
        }
        log.info("Table 'analysis_category' updated successfully");
    }

}
