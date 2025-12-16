package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.AnalysisCategoryDto;
import it.unict.davidemilazzo.claire.dto.AnalysisIdCategoryName;
import it.unict.davidemilazzo.claire.dto.AnalysisIdsCategoryName;
import it.unict.davidemilazzo.claire.service.AnalysisCategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/analysis/category")
@RequiredArgsConstructor
public class AnalysisCategoryController {

    private final AnalysisCategoryService analysisCategoryService;
    private static final Logger log = LogManager.getLogger(AnalysisCategoryController.class);

    @Tag(name = "category")
    @Operation(summary = "Shows all analysis categories")
    @GetMapping("/all")
    public ResponseEntity<Set<AnalysisCategoryDto>> getAll() {

        log.info("[GET /analysis/category/all] Request received: fetching all analysis categories");

        Set<AnalysisCategoryDto> categories = analysisCategoryService.getAll();

        log.info("[GET /analysis/category/all] Returning {} categories", categories.size());

        return ResponseEntity.ok(categories);
    }

    @Tag(name = "category")
    @Operation(summary = "Shows all category names of an analysis")
    @GetMapping("/names/{analysisId}")
    public ResponseEntity<List<AnalysisIdCategoryName>> getNamesByAnalysisId(@PathVariable Long analysisId) {

        log.info("[GET /analysis/category/names/{}] Request received: fetching all categories names from analysis {}", analysisId, analysisId);

        List<AnalysisIdCategoryName> categories = analysisCategoryService.findCategoriesByAnalysisId(analysisId);

        log.info("[GET /analysis/category/names/{}] Returning {} categories", analysisId, categories.size());

        return ResponseEntity.ok(categories);
    }

    @Tag(name = "category")
    @Operation(summary = "Shows all analysis categories of an analysis list")
    @GetMapping("/analysisids")
    public ResponseEntity<List<AnalysisIdsCategoryName>> getBatchAnalysisCategories(@RequestParam List<Long> analysisIds) {

        log.info("[GET /analysis/category/analysisids] Request received: fetching categories for analysisIds={}", analysisIds);

        List<AnalysisIdsCategoryName> result =
                analysisCategoryService.findBatchAnalysisCategoryPairs(analysisIds);

        log.info("[GET /analysis/category/analysisids] Returning {} category pairs", result.size());

        return ResponseEntity.ok(result);
    }
}

