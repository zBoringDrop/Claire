package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.AnalysisCategoryDto;
import it.unict.davidemilazzo.claire.dto.AnalysisIdCategoryName;
import it.unict.davidemilazzo.claire.dto.AnalysisIdsCategoryName;
import it.unict.davidemilazzo.claire.exception.CategoryNotFoundException;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.AnalysisCategoryMapper;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryEntity;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;
import it.unict.davidemilazzo.claire.respository.AnalysisCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("analysis_category-mysql")
@RequiredArgsConstructor
public class AnalysisCategoryDataAccess implements AnalysisCategoryDao {

    private final AnalysisCategoryRepository analysisCategoryRepository;

    @Override
    public Set<AnalysisCategoryDto> getAll() {
        Set<AnalysisCategoryEntity> entitySet = new HashSet<>(analysisCategoryRepository.findAll());
        return AnalysisCategoryMapper.INSTANCE.toDtoSet(entitySet);
    }

    @Override
    public Set<AnalysisCategoryDto> findByAnalysisEntities_Id(Long analysisId) {
        Set<AnalysisCategoryEntity> entitySet = new HashSet<>(analysisCategoryRepository.findByAnalysisEntities_Id(analysisId));
        return AnalysisCategoryMapper.INSTANCE.toDtoSet(entitySet);
    }

    @Override
    public List<AnalysisIdCategoryName> findCategoriesByAnalysisId(Long analysisId) {
        return analysisCategoryRepository.findCategoriesByAnalysisId(analysisId);
    }

    @Override
    public List<AnalysisIdsCategoryName> findBatchAnalysisCategoryPairs(List<Long> analysisIds) {
        return analysisCategoryRepository.findBatchAnalysisCategoryPairs(analysisIds);
    }

    @Override
    public AnalysisCategoryDto findById(Long id) {
        AnalysisCategoryEntity analysisCategory = analysisCategoryRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return AnalysisCategoryMapper.INSTANCE.toDto(analysisCategory);
    }

    @Override
    public AnalysisCategoryDto findByType(AnalysisCategoryType type) {
        AnalysisCategoryEntity analysisCategoryEntity = analysisCategoryRepository.findByType(type);

        if (analysisCategoryEntity == null) {
           throw new CategoryNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND);
        }
        return AnalysisCategoryMapper.INSTANCE.toDto(analysisCategoryEntity);
    }

    @Override
    public boolean exists(Long id) {
        return analysisCategoryRepository.existsById(id);
    }

    @Override
    public boolean existsByType(AnalysisCategoryType type) {
        return analysisCategoryRepository.existsByType(type);
    }
}
