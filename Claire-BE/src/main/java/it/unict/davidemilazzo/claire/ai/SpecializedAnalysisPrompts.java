package it.unict.davidemilazzo.claire.ai;

import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpecializedAnalysisPrompts {

    // ============================================================================
    // SPECIALIZED PROMPT 1: SECURITY ANALYSIS
    // ============================================================================

    public static String getSecurityPrompt(String code) {
        return String.format("""
            SECURITY ANALYSIS - Find security vulnerabilities only.
            
            CODE:
            ```
            %s
            ```
            
            FIND CRITICAL ISSUES:
            - SQL Injection: String concatenation in queries
            - Hardcoded Credentials: API keys, passwords, tokens
            - Input Validation: Missing sanitization
            - Cryptographic Issues: Weak algorithms, bad key management
            - Authentication/Authorization: Missing or flawed checks
            - XSS/CSRF: Unescaped output
            - Information Disclosure: Exposed sensitive data
            
            SEVERITY LEVELS:
            5 = Critical exploit risk, immediate fix required
            4 = High risk, fix before deployment
            3 = Medium risk, should fix soon
            2 = Low risk, fix when possible
            1 = Minimal risk, minor concern
            0 = No security issues found
            
            For each vulnerability:
            - location: Exact line/method/variable
            - type: Vulnerability category
            - severity: 0-5 rating
            - desc: What's vulnerable
            - why: Exploit risk (1-2 sentences)
            - fix: Concrete solution with code
            - before: Vulnerable code snippet
            - after: Fixed code snippet
            
            IMPORTANT: If no security issues found, return empty "issues" array.
            Overall severity = highest individual severity found (0 if none).
            """, code);
    }

    public static String getSecuritySchema() {
        return """
        {
            "type": "object",
            "properties": {
                "overall_severity": { "type": "integer", "minimum": 0, "maximum": 5 },
                "issues": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "location": { "type": "string" },
                            "type": { "type": "string" },
                            "severity": { "type": "integer", "minimum": 1, "maximum": 5 },
                            "desc": { "type": "string" },
                            "why": { "type": "string" },
                            "fix": { "type": "string" },
                            "before": { "type": "string" },
                            "after": { "type": "string" }
                        },
                        "required": ["location", "type", "severity", "desc", "why", "fix", "before", "after"]
                    }
                }
            },
            "required": ["overall_severity", "issues"]
        }
        """;
    }

    // ============================================================================
    // SPECIALIZED PROMPT 2: RELIABILITY & BUGS
    // ============================================================================

    public static String getReliabilityPrompt(String code) {
        return String.format("""
            RELIABILITY & BUG ANALYSIS - Find bugs that cause failures.
            
            CODE:
            ```
            %s
            ```
            
            FIND BUGS:
            - Null Pointer: Missing null checks
            - Array Bounds: IndexOutOfBounds risks
            - Resource Leaks: Unclosed files, streams, connections
            - Memory Leaks: Objects not released
            - Race Conditions: Unsafe concurrent access
            - Logic Errors: Wrong conditions, off-by-one
            - Exception Issues: Swallowed exceptions, missing handlers
            
            SEVERITY LEVELS:
            5 = Certain crash/failure, fix immediately
            4 = High failure risk, fix before production
            3 = Moderate risk, intermittent failures
            2 = Low risk, edge case failures
            1 = Minimal risk, rare failures
            0 = No reliability issues found
            
            For each bug:
            - location: Exact line/method/variable
            - type: Bug category
            - severity: 0-5 rating
            - desc: What fails
            - why: Failure cause (1-2 sentences)
            - scenario: When it breaks
            - fix: Solution with code
            - before: Buggy code
            - after: Fixed code
            
            IMPORTANT: If no bugs found, return empty "issues" array.
            Overall severity = highest individual severity (0 if none).
            """, code);
    }

    public static String getReliabilitySchema() {
        return """
        {
            "type": "object",
            "properties": {
                "overall_severity": { "type": "integer", "minimum": 0, "maximum": 5 },
                "issues": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "location": { "type": "string" },
                            "type": { "type": "string" },
                            "severity": { "type": "integer", "minimum": 1, "maximum": 5 },
                            "desc": { "type": "string" },
                            "why": { "type": "string" },
                            "scenario": { "type": "string" },
                            "fix": { "type": "string" },
                            "before": { "type": "string" },
                            "after": { "type": "string" }
                        },
                        "required": ["location", "type", "severity", "desc", "why", "scenario", "fix", "before", "after"]
                    }
                }
            },
            "required": ["overall_severity", "issues"]
        }
        """;
    }

    // ============================================================================
    // SPECIALIZED PROMPT 3: PERFORMANCE ANALYSIS
    // ============================================================================

    public static String getPerformancePrompt(String code) {
        return String.format("""
            PERFORMANCE ANALYSIS - Find bottlenecks and inefficiencies.
            
            CODE:
            ```
            %s
            ```
            
            FIND PERFORMANCE ISSUES:
            - Algorithm: O(n²) where O(n log n) possible
            - Database: N+1 queries, missing indexes
            - Memory: Excessive allocations, large retentions
            - I/O: Blocking operations
            - Strings: Concatenation in loops
            - Cache: Missing caching for expensive operations
            - Collections: Wrong data structure choice
            
            SEVERITY LEVELS:
            5 = Critical bottleneck, unusable at scale
            4 = Major slowdown, impacts user experience
            3 = Moderate inefficiency, noticeable delay
            2 = Minor inefficiency, slight overhead
            1 = Negligible impact, micro-optimization
            0 = No performance issues found
            
            For each issue:
            - location: Exact line/method
            - type: Performance problem category
            - severity: 0-5 rating
            - desc: What's slow
            - why: Bottleneck cause (1-2 sentences)
            - impact: Time/memory/throughput effect
            - fix: Optimization with code
            - before: Slow code
            - after: Optimized code
            - gain: Expected improvement (e.g., "10x faster")
            
            IMPORTANT: If no performance issues found, return empty "issues" array.
            Overall severity = highest individual severity (0 if none).
            """, code);
    }

    public static String getPerformanceSchema() {
        return """
        {
            "type": "object",
            "properties": {
                "overall_severity": { "type": "integer", "minimum": 0, "maximum": 5 },
                "issues": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "location": { "type": "string" },
                            "type": { "type": "string" },
                            "severity": { "type": "integer", "minimum": 1, "maximum": 5 },
                            "desc": { "type": "string" },
                            "why": { "type": "string" },
                            "impact": { "type": "string" },
                            "fix": { "type": "string" },
                            "before": { "type": "string" },
                            "after": { "type": "string" },
                            "gain": { "type": "string" }
                        },
                        "required": ["location", "type", "severity", "desc", "why", "impact", "fix", "before", "after", "gain"]
                    }
                }
            },
            "required": ["overall_severity", "issues"]
        }
        """;
    }

    // ============================================================================
    // SPECIALIZED PROMPT 4: ARCHITECTURE & DESIGN
    // ============================================================================

    public static String getArchitecturePrompt(String code) {
        return String.format("""
            ARCHITECTURE & DESIGN ANALYSIS - Find structural problems.
            
            CODE:
            ```
            %s
            ```
            
            FIND DESIGN ISSUES:
            - SOLID Violations: SRP, OCP, LSP, ISP, DIP
            - Coupling: Tight dependencies between modules
            - Cohesion: Unrelated functionality grouped
            - Patterns: Missing or misused patterns
            - Separation: Mixed concerns (business/data/UI)
            - Abstraction: Wrong abstraction level
            
            SEVERITY LEVELS:
            5 = Critical design flaw, major refactor needed
            4 = Significant issue, hard to maintain/extend
            3 = Moderate issue, impacts maintainability
            2 = Minor issue, slight maintenance overhead
            1 = Negligible issue, minor improvement
            0 = No architecture issues found
            
            For each issue:
            - location: Class/method
            - type: Design principle/pattern
            - severity: 0-5 rating
            - desc: What's wrong architecturally
            - why: Impact on maintainability (1-2 sentences)
            - principle: Which principle violated
            - fix: Refactoring approach with example
            - benefit: Improvement from fixing
            
            IMPORTANT: If no architecture issues found, return empty "issues" array.
            Overall severity = highest individual severity (0 if none).
            """, code);
    }

    public static String getArchitectureSchema() {
        return """
        {
            "type": "object",
            "properties": {
                "overall_severity": { "type": "integer", "minimum": 0, "maximum": 5 },
                "issues": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "location": { "type": "string" },
                            "type": { "type": "string" },
                            "severity": { "type": "integer", "minimum": 1, "maximum": 5 },
                            "desc": { "type": "string" },
                            "why": { "type": "string" },
                            "principle": { "type": "string" },
                            "fix": { "type": "string" },
                            "benefit": { "type": "string" }
                        },
                        "required": ["location", "type", "severity", "desc", "why", "principle", "fix", "benefit"]
                    }
                }
            },
            "required": ["overall_severity", "issues"]
        }
        """;
    }

    // ============================================================================
    // SPECIALIZED PROMPT 5: CODE STRUCTURE & COMPLEXITY
    // ============================================================================

    public static String getStructurePrompt(String code) {
        return String.format("""
            STRUCTURE & COMPLEXITY ANALYSIS - Find complexity problems.
            
            CODE:
            ```
            %s
            ```
            
            FIND COMPLEXITY ISSUES:
            - Cyclomatic: Too many branches (>10)
            - Nesting: Deep nesting (>4 levels)
            - Length: Long methods (>50 lines) or classes (>300)
            - Parameters: Too many params (>5)
            - Duplication: Repeated code (DRY violations)
            - Dead Code: Unused variables, methods, imports
            
            SEVERITY LEVELS:
            5 = Unmaintainable, must refactor
            4 = Very complex, hard to understand
            3 = Moderately complex, should simplify
            2 = Slightly complex, minor issue
            1 = Acceptable complexity
            0 = No complexity issues found
            
            For each issue:
            - location: Class/method
            - type: Complexity category
            - severity: 0-5 rating
            - desc: Complexity problem
            - why: Why hard to maintain (1-2 sentences)
            - metric: Complexity value (e.g., "cyclomatic: 15")
            - fix: Simplification approach with example
            - reduced: Expected complexity after fix
            
            IMPORTANT: If no complexity issues found, return empty "issues" array.
            Overall severity = highest individual severity (0 if none).
            """, code);
    }

    public static String getStructureSchema() {
        return """
        {
            "type": "object",
            "properties": {
                "overall_severity": { "type": "integer", "minimum": 0, "maximum": 5 },
                "issues": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "location": { "type": "string" },
                            "type": { "type": "string" },
                            "severity": { "type": "integer", "minimum": 1, "maximum": 5 },
                            "desc": { "type": "string" },
                            "why": { "type": "string" },
                            "metric": { "type": "string" },
                            "fix": { "type": "string" },
                            "reduced": { "type": "string" }
                        },
                        "required": ["location", "type", "severity", "desc", "why", "metric", "fix", "reduced"]
                    }
                }
            },
            "required": ["overall_severity", "issues"]
        }
        """;
    }

    // ============================================================================
    // SPECIALIZED PROMPT 6: NAMING & DOCUMENTATION
    // ============================================================================

    public static String getNamingPrompt(String code) {
        return String.format("""
            NAMING & DOCUMENTATION ANALYSIS - Find readability issues.
            
            CODE:
            ```
            %s
            ```
            
            FIND READABILITY ISSUES:
            - Unclear Names: Variables like 'a', 'temp', 'data'
            - Inconsistent Style: Mixed naming conventions
            - Misleading Names: Name doesn't match behavior
            - Too Generic: 'manager', 'handler', 'util'
            - Magic Values: Unexplained constants
            - Missing Docs: Public APIs without comments
            - Outdated Comments: Don't match code
            
            SEVERITY LEVELS:
            5 = Critically unclear, impossible to understand
            4 = Very confusing, major readability issue
            3 = Moderately unclear, impacts understanding
            2 = Slightly unclear, minor confusion
            1 = Minor naming preference
            0 = No readability issues found
            
            For each issue:
            - location: Variable/method/class
            - type: Naming or documentation issue
            - severity: 0-5 rating
            - current: Current name or doc problem
            - desc: Why it's unclear
            - suggested: Better alternative
            - clarity: How it improves readability
            
            IMPORTANT: If no naming issues found, return empty "issues" array.
            Overall severity = highest individual severity (0 if none).
            """, code);
    }

    public static String getNamingSchema() {
        return """
        {
            "type": "object",
            "properties": {
                "overall_severity": { "type": "integer", "minimum": 0, "maximum": 5 },
                "issues": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "location": { "type": "string" },
                            "type": { "type": "string" },
                            "severity": { "type": "integer", "minimum": 1, "maximum": 5 },
                            "current": { "type": "string" },
                            "desc": { "type": "string" },
                            "suggested": { "type": "string" },
                            "clarity": { "type": "string" }
                        },
                        "required": ["location", "type", "severity", "current", "desc", "suggested", "clarity"]
                    }
                }
            },
            "required": ["overall_severity", "issues"]
        }
        """;
    }

    // ============================================================================
    // FIELD MAPPING REFERENCE
    // ============================================================================

    public static String getFieldMapping() {
        return """
        COMPACT FIELD NAMES MAPPING:
        
        Common Fields (All Analyses):
        - overall_severity: 0-5 rating of entire analysis
        - issues: Array of found problems (empty if none)
        - location: Where the issue is (line/method/class/variable)
        - type: Category of issue
        - severity: Individual issue severity (1-5)
        - desc: Description of the problem
        - why: Why it's a problem (1-2 sentences)
        - fix: How to fix it
        
        Security Specific:
        - before: Vulnerable code snippet
        - after: Fixed code snippet
        
        Reliability Specific:
        - before: Buggy code
        - after: Fixed code
        - scenario: Failure scenario
        
        Performance Specific:
        - before: Slow code
        - after: Optimized code
        - impact: Performance impact description
        - gain: Expected improvement
        
        Architecture Specific:
        - principle: Design principle violated
        - benefit: Benefits of fixing
        
        Structure Specific:
        - metric: Complexity metric value
        - reduced: Expected complexity after fix
        
        Naming Specific:
        - current: Current name/documentation
        - suggested: Better alternative
        - clarity: Clarity improvement description
        """;
    }

    public static String appendAlreadyFoundIssues(String basePrompt, List<String> foundIssues) {
        StringBuilder strToAppend = new StringBuilder();
        strToAppend.append("\n\nALREADY FOUND - DO NOT REPORT AGAIN:\n");
        for (String issue : foundIssues) {
            strToAppend.append("✗ ").append(issue).append("\n");
        }
        strToAppend.append("\nReport ONLY new issues not in this list!\n");

        return basePrompt.concat(strToAppend.toString());
    }

    public static List<PromptGenerationResult> getPromptsByCategory(List<AnalysisCategoryType> analysisCategories, String code) {
        List<PromptGenerationResult> promptGenerationResults = new ArrayList<>();

        if (analysisCategories.contains(AnalysisCategoryType.SECURITY)) {
            promptGenerationResults.add(new PromptGenerationResult(
                    AnalysisCategoryType.SECURITY,
                    getSecurityPrompt(code),
                    getSecuritySchema()));
        }
        if (analysisCategories.contains(AnalysisCategoryType.RELIABILITY_AND_BUGS)) {
            promptGenerationResults.add(new PromptGenerationResult(
                    AnalysisCategoryType.RELIABILITY_AND_BUGS,
                    getReliabilityPrompt(code),
                    getReliabilitySchema()));
        }
        if (analysisCategories.contains(AnalysisCategoryType.PERFORMANCE)) {
            promptGenerationResults.add(new PromptGenerationResult(
                    AnalysisCategoryType.PERFORMANCE,
                    getPerformancePrompt(code),
                    getPerformanceSchema()));
        }
        if (analysisCategories.contains(AnalysisCategoryType.ARCHITECTURE_AND_DESIGN)) {
            promptGenerationResults.add(new PromptGenerationResult(
                    AnalysisCategoryType.ARCHITECTURE_AND_DESIGN,
                    getArchitecturePrompt(code),
                    getArchitectureSchema()));
        }
        if (analysisCategories.contains(AnalysisCategoryType.CODE_STRUCTURE_AND_COMPLEXITY)) {
            promptGenerationResults.add(new PromptGenerationResult(
                    AnalysisCategoryType.CODE_STRUCTURE_AND_COMPLEXITY,
                    getStructurePrompt(code),
                    getStructureSchema()));
        }
        if (analysisCategories.contains(AnalysisCategoryType.NAMING_AND_DOCUMENTATION)) {
            promptGenerationResults.add(new PromptGenerationResult(
                    AnalysisCategoryType.NAMING_AND_DOCUMENTATION,
                    getNamingPrompt(code),
                    getNamingSchema()));
        }

        return promptGenerationResults;
    }

    public static List<PromptGenerationResult> getAllPrompts(String code) {
        return List.of(
                new PromptGenerationResult(AnalysisCategoryType.SECURITY, getSecurityPrompt(code), getSecuritySchema()),
                new PromptGenerationResult(AnalysisCategoryType.RELIABILITY_AND_BUGS, getReliabilityPrompt(code), getReliabilitySchema()),
                new PromptGenerationResult(AnalysisCategoryType.PERFORMANCE, getPerformancePrompt(code), getPerformanceSchema()),
                new PromptGenerationResult(AnalysisCategoryType.ARCHITECTURE_AND_DESIGN, getArchitecturePrompt(code), getArchitectureSchema()),
                new PromptGenerationResult(AnalysisCategoryType.CODE_STRUCTURE_AND_COMPLEXITY, getStructurePrompt(code), getStructureSchema()),
                new PromptGenerationResult(AnalysisCategoryType.NAMING_AND_DOCUMENTATION, getNamingPrompt(code), getNamingSchema())
        );
    }
}
