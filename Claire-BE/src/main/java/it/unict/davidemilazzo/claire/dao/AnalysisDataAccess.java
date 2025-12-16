package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.AnalysisDto;
import it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.AnalysisMapper;
import it.unict.davidemilazzo.claire.model.AnalysisEntity;
import it.unict.davidemilazzo.claire.model.AnalysisStatus;
import it.unict.davidemilazzo.claire.respository.AnalysisEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository("analysis-mysql")
public class AnalysisDataAccess implements AnalysisDao {

    private final AnalysisEntityRepository analysisEntityRepository;
    private static final Logger log = LogManager.getLogger(AnalysisDataAccess.class);


    @Override
    public AnalysisDto createNew(AnalysisDto analysisDto) {
        AnalysisEntity analysisEntity = AnalysisMapper.INSTANCE.toEntity(analysisDto);
        log.info("Dto: {}", analysisDto.toString());
        log.info("Enityt: {}", analysisEntity.getCreatedAt());
        return AnalysisMapper.INSTANCE.toDto(analysisEntityRepository.save(analysisEntity));
    }

    @Override
    public AnalysisDto update(AnalysisDto analysisDto) {
        AnalysisEntity analysisEntityToUpdate = analysisEntityRepository.findById(analysisDto.getId())
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        AnalysisEntity analysisEntityExisting = AnalysisMapper.INSTANCE.toEntity(analysisDto);

        if (analysisDto.getStatus() != null) {
            analysisEntityToUpdate.setStatus(analysisDto.getStatus());
        }

        if (analysisDto.getMessage() != null) {
            analysisEntityToUpdate.setMessage(analysisDto.getMessage());
        }

        if (analysisDto.getFileId() != null) {
            analysisEntityToUpdate.setFileEntity(analysisEntityExisting.getFileEntity());
        }

        if (analysisDto.getCodeSnippetId() != null) {
            analysisEntityToUpdate.setCodeSnippetEntity(analysisEntityExisting.getCodeSnippetEntity());
        }

        if (analysisDto.getToolId() != null) {
            analysisEntityToUpdate.setToolEntity(analysisEntityExisting.getToolEntity());
        }

        analysisEntityToUpdate.setCategories(analysisEntityExisting.getCategories());

        if (analysisDto.getResultJson() != null) {
            analysisEntityToUpdate.setResultJson(analysisDto.getResultJson());
        }

        if (analysisDto.getEndDatetime() != null) {
            analysisEntityToUpdate.setEndDatetime(analysisDto.getEndDatetime());
        }

        if (analysisDto.getExecutionMs() != null) {
            analysisEntityToUpdate.setExecutionMs(analysisDto.getExecutionMs());
        }

        if (analysisDto.getOverallSeverity() != null) {
            analysisEntityToUpdate.setOverallSeverity(analysisDto.getOverallSeverity());
        }

        if (analysisDto.getOutputLength() != null) {
            analysisEntityToUpdate.setOutputLength(analysisDto.getOutputLength());
        }

        if (analysisDto.getIssuesCount() != null) {
            analysisEntityToUpdate.setIssuesCount(analysisDto.getIssuesCount());
        }

        if (analysisDto.getCreatedAt() != null) {
            analysisEntityToUpdate.setCreatedAt(analysisDto.getCreatedAt());
        }

        return AnalysisMapper.INSTANCE.toDto(analysisEntityRepository.save(analysisEntityToUpdate));
    }

    @Override
    public AnalysisDto delete(Long id) {
        AnalysisEntity analysisEntityToDelete = analysisEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        analysisEntityRepository.delete(analysisEntityToDelete);
        return AnalysisMapper.INSTANCE.toDto(analysisEntityToDelete);
    }

    @Override
    public AnalysisDto findById(Long id) {
        AnalysisEntity analysisEntity = analysisEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return AnalysisMapper.INSTANCE.toDto(analysisEntity);
    }

    @Override
    public List<AnalysisDto> findByFileEntity_id(Long id) {
        List<AnalysisEntity> analysisEntities = analysisEntityRepository.findByFileEntity_id(id);
        return AnalysisMapper.INSTANCE.toDtoList(analysisEntities);
    }

    @Override
    public List<AnalysisDto> findByCodeSnippetEntity_id(Long id) {
        List<AnalysisEntity> analysisEntities = analysisEntityRepository.findByCodeSnippetEntity_id(id);
        return AnalysisMapper.INSTANCE.toDtoList(analysisEntities);
    }

    @Override
    public List<AnalysisDto> findByToolEntity_id(Long id) {
        List<AnalysisEntity> analysisEntities = analysisEntityRepository.findByToolEntity_id(id);
        return AnalysisMapper.INSTANCE.toDtoList(analysisEntities);
    }

    @Override
    public List<AnalysisDto> findByStatus(AnalysisStatus status) {
        List<AnalysisEntity> analysisEntities = analysisEntityRepository.findByStatus(status);
        return AnalysisMapper.INSTANCE.toDtoList(analysisEntities);
    }

    @Override
    public List<AnalysisPreviewDto> getAllUserPreviews(Long userId) {
        return analysisEntityRepository.findAllPreviewsByUser(userId);
    }

    @Override
    public List<AnalysisPreviewDto> findAllPreviewsByUserAndStatus(Long userId, AnalysisStatus status) {
        return analysisEntityRepository.findAllPreviewsByUserAndStatus(userId, status);
    }

    @Override
    public List<AnalysisPreviewDto> findAllPreviewsByUserAndStates(Long userId, List<AnalysisStatus> analysisStates) {
        return analysisEntityRepository.findAllPreviewsByUserAndStates(userId, analysisStates);
    }

    @Override
    public List<AnalysisPreviewDto> findAllPreviewsByUserAndFileId(Long userId, Long fileId) {
        return analysisEntityRepository.findAllPreviewsByUserAndFileId(userId, fileId);
    }

    @Override
    public List<AnalysisPreviewDto> findAllPreviewsByUserAndSnippetId(Long userId, Long snippetId) {
        return analysisEntityRepository.findAllPreviewsByUserAndSnippetId(userId, snippetId);
    }

    @Override
    public void updateStatus(Long id, AnalysisStatus status) {
        analysisEntityRepository.updateStatus(id, status);
    }

    @Override
    public void updateStatusMessage(Long id, String message) {
        analysisEntityRepository.updateStatusMessage(id, message);
    }

    @Override
    public boolean exists(Long id) {
        return analysisEntityRepository.existsById(id);
    }
}
