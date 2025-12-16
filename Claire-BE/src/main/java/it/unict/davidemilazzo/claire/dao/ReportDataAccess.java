package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.ReportDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.ReportMapper;
import it.unict.davidemilazzo.claire.model.ReportEntity;
import it.unict.davidemilazzo.claire.respository.ReportEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository("report-mysql")
@RequiredArgsConstructor
public class ReportDataAccess implements ReportDao {

    private final ReportEntityRepository reportEntityRepository;

    @Override
    public ReportDto saveNew(ReportDto reportDto) {
        if (reportDto.getAnalysisId() == null) {
            throw new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND);
        }
        ReportEntity reportEntity = ReportMapper.INSTANCE.toEntity(reportDto);
        return ReportMapper.INSTANCE.toDto(reportEntity);
    }

    @Override
    public ReportDto findById(Long id) {
        ReportEntity reportEntity = reportEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ReportMapper.INSTANCE.toDto(reportEntity);
    }

    @Override
    public ReportDto findByAnalysisEntity_id(Long analysisId) {
        ReportEntity reportEntity = reportEntityRepository.findByAnalysisEntity_id(analysisId)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ReportMapper.INSTANCE.toDto(reportEntity);
    }

    @Override
    public ReportDto findByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId) {
        ReportEntity reportEntity = reportEntityRepository.findByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(userId, analysisId)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ReportMapper.INSTANCE.toDto(reportEntity);    }

    @Override
    public ReportDto findByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId) {
        ReportEntity reportEntity = reportEntityRepository.findByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(userId, analysisId)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ReportMapper.INSTANCE.toDto(reportEntity);    }

    @Override
    public ReportDto delete(Long id) {
        ReportEntity reportEntityToDelete = reportEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        reportEntityRepository.delete(reportEntityToDelete);
        return ReportMapper.INSTANCE.toDto(reportEntityToDelete);
    }

    @Override
    public boolean exists(Long id) {
        return reportEntityRepository.existsById(id);
    }

    @Override
    public boolean existsByAnalysisEntity_id(Long id) {
        return reportEntityRepository.existsByAnalysisEntity_id(id);
    }

    @Override
    public boolean existsByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId) {
        return reportEntityRepository.existsByAnalysisEntity_CodeSnippetEntity_UserEntity_IdAndAnalysisEntity_Id(userId, analysisId);
    }

    @Override
    public boolean existsByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(Long userId, Long analysisId) {
        return reportEntityRepository.existsByAnalysisEntity_FileEntity_UserEntity_IdAndAnalysisEntity_Id(userId, analysisId);
    }
}
