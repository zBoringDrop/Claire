package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.FileDao;
import it.unict.davidemilazzo.claire.dto.FileDto;
import it.unict.davidemilazzo.claire.dto.FilePreviewDto;
import it.unict.davidemilazzo.claire.dto.ProgrammingLanguageDto;
import it.unict.davidemilazzo.claire.exception.*;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import it.unict.davidemilazzo.claire.util.ProgrammingLanguageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final Logger log = LogManager.getLogger(FileService.class);
    private final FileDao fileDao;

    private final ProgrammingLanguageService programmingLanguageService;

    @Transactional
    public FileDto uploadNew(MultipartFile file, Long userId) {
        log.info("User {} sent a file: {}", userId, file);
        if (file.isEmpty()) {
            log.info("File is empty: {}", file);
            throw new FileEmptyException(ExceptionMessages.FILE_IS_EMPTY);
        }

        FileDto fileDto = generateFileDtoFromFile(file, userId);
        return fileDao.uploadNew(fileDto);
    }

    public FileDto generateFileDtoFromFile(MultipartFile file, Long userId) {
        Long programmingLanguageId = getFileExtId(file.getOriginalFilename());

        try {
            FileDto fileDto = new FileDto(null,
                                            userId,
                                            file.getOriginalFilename(),
                                            file.getContentType(),
                                            LocalDateTime.now(),
                                            programmingLanguageId,
                                            file.getSize(),
                                            new String(file.getBytes(), StandardCharsets.UTF_8),
                                            false);
            log.info("Generated DTO object: {} (original file: {})", fileDto, file);
            return fileDto;
        } catch (IOException e) {
            throw new FileReadException(ExceptionMessages.ERROR_ON_FILE_READ);
        }
    }

    @Transactional
    public FileDto delete(Long id) {
        return fileDao.delete(id);
    }

    public List<FileDto> listUserFiles(Long userId) {
        return fileDao.findByUserId(userId);
    }

    public FileDto findById(Long fileId) {
        return fileDao.findById(fileId);
    }

    public FilePreviewDto findPreviewById(Long fileId) {
        return fileDao.findPreviewById(fileId);
    }

    public List<FilePreviewDto> listAllUserFilePreviews(Long userId) {
        return fileDao.listAllUserFilePreviews(userId);
    }

    public void isOwner(Long fileIdToCheck, Long userId) {
        FileDto fileDto = fileDao.findById(fileIdToCheck);

        if (!Objects.equals(userId, fileDto.getUserId())) {
            throw new NotTheOwnerException(ExceptionMessages.NOT_THE_OWNER);
        }
    }

    public boolean exists(Long fileId) {
        return fileDao.exists(fileId);
    }

    private String getFileExtFromTitle(String title) {
        if (title == null || !title.contains(".")) {
            return "";
        }

        return title.substring(title.lastIndexOf('.') + 1).toLowerCase();
    }

    private Long getFileExtId(String title) {
        String rawFileExt = getFileExtFromTitle(title);

        Optional<ProgrammingLanguage> ext = ProgrammingLanguageUtils.fromExtension(rawFileExt);

        if (ext.isPresent()) {
            return programmingLanguageService.findByName(ext.get().toString()).getId();
        }
        return programmingLanguageService.findByName(ProgrammingLanguage.PSEUDO_CODE.name()).getId();
    }

}
