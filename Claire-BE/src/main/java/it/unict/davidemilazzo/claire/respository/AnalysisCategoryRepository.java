package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.dto.AnalysisIdCategoryName;
import it.unict.davidemilazzo.claire.dto.AnalysisIdsCategoryName;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryEntity;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnalysisCategoryRepository extends JpaRepository<AnalysisCategoryEntity, Long> {
    AnalysisCategoryEntity findByType(AnalysisCategoryType type);
    List<AnalysisCategoryEntity> findByAnalysisEntities_Id(Long analysisId);
    boolean existsByType(AnalysisCategoryType type);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.AnalysisIdCategoryName(
            c.id,
            CAST(c.type AS string)
        )
        FROM AnalysisEntity a
        JOIN a.categories c
        WHERE a.id = :analysisId
        ORDER BY c.type ASC
    """)
    List<AnalysisIdCategoryName> findCategoriesByAnalysisId(@Param("analysisId") Long analysisId);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.AnalysisIdsCategoryName(
            a.id,
            CAST(c.type AS string)
        )
        FROM AnalysisEntity a
        JOIN a.categories c
        WHERE a.id IN :analysisIds
    """)
    List<AnalysisIdsCategoryName> findBatchAnalysisCategoryPairs(@Param("analysisIds") List<Long> analysisIds);

}
