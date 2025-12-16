package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.AnalysisCategoryDto;
import it.unict.davidemilazzo.claire.dto.AnalysisIdCategoryName;
import it.unict.davidemilazzo.claire.dto.AnalysisIdsCategoryName;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;

import java.util.List;
import java.util.Set;

public interface AnalysisCategoryDao {
    Set<AnalysisCategoryDto> getAll();
    Set<AnalysisCategoryDto> findByAnalysisEntities_Id(Long analysisId);
    List<AnalysisIdCategoryName> findCategoriesByAnalysisId(Long analysisId);
    List<AnalysisIdsCategoryName> findBatchAnalysisCategoryPairs(List<Long> analysisIds);
    AnalysisCategoryDto findById(Long id);
    AnalysisCategoryDto findByType(AnalysisCategoryType type);
    boolean exists(Long id);
    boolean existsByType(AnalysisCategoryType type);
}
