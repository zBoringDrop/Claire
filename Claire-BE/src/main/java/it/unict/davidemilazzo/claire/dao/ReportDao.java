package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.ReportDto;

public interface ReportDao {
    ReportDto saveNew(ReportDto reportDto);
    ReportDto findById(Long id);
    ReportDto findByAnalysisEntity_id(Long analysisId);
    ReportDto findByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
    ReportDto findByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
    ReportDto delete(Long id);
    boolean exists(Long id);
    boolean existsByAnalysisEntity_id(Long id);
    boolean existsByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
    boolean existsByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId);
}
