package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.AnalysisCategoryDao;
import it.unict.davidemilazzo.claire.dto.AnalysisCategoriesDto;
import it.unict.davidemilazzo.claire.dto.AnalysisCategoryDto;
import it.unict.davidemilazzo.claire.dto.AnalysisIdCategoryName;
import it.unict.davidemilazzo.claire.dto.AnalysisIdsCategoryName;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalysisCategoryService {
    private static final Logger log = LogManager.getLogger(AnalysisCategoryService.class);
    private final AnalysisCategoryDao analysisCategoryDao;

    public Set<AnalysisCategoryDto> getAll() {
        return analysisCategoryDao.getAll();
    }

    public AnalysisCategoryDto findById(Long id) {
        return analysisCategoryDao.findById(id);
    }

    public AnalysisCategoryDto findByType(AnalysisCategoryType type) {
        return analysisCategoryDao.findByType(type);
    }

    public boolean exists(Long id) {
        return analysisCategoryDao.exists(id);
    }

    public boolean existsByType(AnalysisCategoryType type) {
        return analysisCategoryDao.existsByType(type);
    }

    public List<AnalysisIdCategoryName> findCategoriesByAnalysisId(Long analysisId) {
        return analysisCategoryDao.findCategoriesByAnalysisId(analysisId);
    }

    public List<AnalysisIdsCategoryName> findBatchAnalysisCategoryPairs(List<Long> analysisIds) {
        return analysisCategoryDao.findBatchAnalysisCategoryPairs(analysisIds);
    }

}
