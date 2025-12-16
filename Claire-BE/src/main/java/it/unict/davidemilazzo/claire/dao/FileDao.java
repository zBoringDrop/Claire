package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.FileDto;
import it.unict.davidemilazzo.claire.dto.FilePreviewDto;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import it.unict.davidemilazzo.claire.model.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface FileDao {
    FileDto uploadNew(FileDto fileDto);
    List<FileDto> getAll();
    FileDto delete(Long id);
    Long countAll();
    boolean exists(Long id);
    FileDto findById(Long id);
    List<FileDto> findByUserEntity(UserEntity userEntity);
    List<FileDto> findByUserId(Long userId);
    FilePreviewDto findPreviewById(Long fileId);
    List<FileDto> findByFilename(String filename);
    List<FileDto> findByLanguage(ProgrammingLanguage programmingLanguage);
    List<FileDto> findByUploadDatetime(LocalDateTime localDateTime);
    List<FilePreviewDto> listAllUserFilePreviews(Long userId);
}
