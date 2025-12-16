package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.FileDto;
import it.unict.davidemilazzo.claire.dto.FilePreviewDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.FileMapper;
import it.unict.davidemilazzo.claire.model.FileEntity;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.respository.FileEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository("file-mysql")
public class FileDataAccess implements FileDao {

    private final FileEntityRepository fileEntityRepository;

    @Override
    public FileDto uploadNew(FileDto fileDto) {
        FileEntity fileEntity = FileMapper.INSTANCE.toEntity(fileDto);
        return FileMapper.INSTANCE.toDto(fileEntityRepository.save(fileEntity));
    }

    @Override
    public List<FileDto> getAll() {
        List<FileEntity> fileEntities = fileEntityRepository.findAll();
        return FileMapper.INSTANCE.toDtoList(fileEntities);
    }

    @Override
    public FileDto delete(Long id) {
        FileEntity fileEntityToDelete = fileEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        fileEntityRepository.delete(fileEntityToDelete);
        return FileMapper.INSTANCE.toDto(fileEntityToDelete);
    }

    @Override
    public Long countAll() {
        return fileEntityRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return fileEntityRepository.existsById(id);
    }

    @Override
    public FileDto findById(Long id) {
        FileEntity fileEntity = fileEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return FileMapper.INSTANCE.toDto(fileEntity);
    }

    @Override
    public List<FileDto> findByUserEntity(UserEntity userEntity) {
        return FileMapper.INSTANCE.toDtoList(fileEntityRepository.findByUserEntity(userEntity));
    }

    @Override
    public List<FileDto> findByUserId(Long userId) {
        return FileMapper.INSTANCE.toDtoList(fileEntityRepository.findByUserEntity_Id(userId));
    }

    @Override
    public FilePreviewDto findPreviewById(Long fileId) {
        return fileEntityRepository.findPreviewById(fileId);
    }

    @Override
    public List<FileDto> findByFilename(String filename) {
        List<FileEntity> fileEntities = fileEntityRepository.findByFilename(filename);
        return FileMapper.INSTANCE.toDtoList(fileEntities);
    }

    @Override
    public List<FileDto> findByLanguage(ProgrammingLanguage programmingLanguage) {
        List<FileEntity> fileEntities = fileEntityRepository.findByLanguageEntity_Name(programmingLanguage.name());
        return FileMapper.INSTANCE.toDtoList(fileEntities);
    }

    @Override
    public List<FileDto> findByUploadDatetime(LocalDateTime localDateTime) {
        List<FileEntity> fileEntities = fileEntityRepository.findByUploadDatetime(localDateTime);
        return FileMapper.INSTANCE.toDtoList(fileEntities);
    }

    @Override
    public List<FilePreviewDto> listAllUserFilePreviews(Long userId) {
        return fileEntityRepository.listAllUserFilePreviews(userId);
    }

}
