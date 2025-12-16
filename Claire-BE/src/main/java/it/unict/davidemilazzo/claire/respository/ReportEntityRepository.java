package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.model.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportEntityRepository extends JpaRepository<ReportEntity, Long> {
    Optional<ReportEntity> findByAnalysisEntity_id(Long id);
    Optional<ReportEntity> findByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
    Optional<ReportEntity> findByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
    boolean existsByAnalysisEntity_id(Long id);
    boolean existsByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
    boolean existsByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
}
