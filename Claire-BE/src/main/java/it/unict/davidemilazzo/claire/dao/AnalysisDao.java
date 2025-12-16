package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.AnalysisDto;
import it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto;
import it.unict.davidemilazzo.claire.model.AnalysisStatus;

import java.util.List;

public interface AnalysisDao {
    AnalysisDto createNew(AnalysisDto analysisDto);
    AnalysisDto update(AnalysisDto analysisDto);
    AnalysisDto delete(Long id);
    AnalysisDto findById(Long id);
    List<AnalysisDto> findByFileEntity_id(Long id);
    List<AnalysisDto> findByCodeSnippetEntity_id(Long id);
    List<AnalysisDto> findByToolEntity_id(Long id);
    List<AnalysisDto> findByStatus(AnalysisStatus status);
    List<AnalysisPreviewDto> getAllUserPreviews(Long userId);
    List<AnalysisPreviewDto> findAllPreviewsByUserAndStatus(Long userId, AnalysisStatus status);
    List<AnalysisPreviewDto> findAllPreviewsByUserAndStates(Long userId, List<AnalysisStatus> analysisStates);
    List<AnalysisPreviewDto> findAllPreviewsByUserAndFileId(Long userId, Long fileId);
    List<AnalysisPreviewDto> findAllPreviewsByUserAndSnippetId(Long userId, Long snippetId);
    void updateStatus(Long id, AnalysisStatus status);
    void updateStatusMessage(Long id, String message);
    boolean exists(Long id);
}
