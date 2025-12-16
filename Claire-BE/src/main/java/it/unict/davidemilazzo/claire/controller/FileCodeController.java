package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.FileDto;
import it.unict.davidemilazzo.claire.dto.FilePreviewDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileCodeController {

    private final FileService fileService;
    private static final Logger log = LogManager.getLogger(FileCodeController.class);

    @Tag(name = "file")
    @Operation(summary = "User upload file")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> uploadFile(@RequestPart("uploadedFile") MultipartFile file,
                                              Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[POST /file/upload] Request received from userId={} fileName={}", userId, file.getOriginalFilename());

        FileDto fileDto = fileService.uploadNew(file, userId);

        log.info("[POST /file/upload] Upload completed for userId={} fileId={}", userId, fileDto.getId());

        return new ResponseEntity<>(fileDto, HttpStatus.CREATED);
    }

    @Tag(name = "file")
    @Operation(summary = "User get his files list")
    @GetMapping("/list")
    public ResponseEntity<List<FileDto>> listUserFiles(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /file/list] Request received from userId={}", userId);

        List<FileDto> fileDtoList = fileService.listUserFiles(userId);

        log.info("[GET /file/list] Returning {} files for userId={}", fileDtoList.size(), userId);

        return ResponseEntity.ok(fileDtoList);
    }

    @Tag(name = "file")
    @Operation(summary = "User get his files preview list")
    @GetMapping("/list/preview")
    public ResponseEntity<List<FilePreviewDto>> listUserPreviewFiles(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /file/list/preview] Request received from userId={}", userId);

        List<FilePreviewDto> fileDtoList = fileService.listAllUserFilePreviews(userId);

        log.info("[GET /file/list/preview] Returning {} files for userId={}", fileDtoList.size(), userId);

        return ResponseEntity.ok(fileDtoList);
    }

    @Tag(name = "file")
    @Operation(summary = "User get his file")
    @GetMapping("/get/{fileId}")
    public ResponseEntity<FileDto> getUserFile(@PathVariable Long fileId,
                                               Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /file/get/{}] Request received from userId={}", fileId, userId);

        fileService.isOwner(fileId, userId);
        log.info("[GET /file/get/{}] Ownership validated for userId={}", fileId, userId);

        FileDto fileDto = fileService.findById(fileId);

        log.info("[GET /file/get/{}] Returning file for userId={}", fileId, userId);

        return ResponseEntity.ok(fileDto);
    }

    @Tag(name = "file")
    @Operation(summary = "User get his file")
    @GetMapping("/get/preview/{fileId}")
    public ResponseEntity<FilePreviewDto> getUserFilePreview(@PathVariable Long fileId,
                                                                Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /file/get/preview/{}] Request received from userId={}", fileId, userId);

        fileService.isOwner(fileId, userId);
        log.info("[GET /file/get/preview/{}] Ownership validated for userId={}", fileId, userId);

        FilePreviewDto fileDto = fileService.findPreviewById(fileId);

        log.info("[GET /file/get/preview/{}] Returning file for userId={}", fileId, userId);

        return ResponseEntity.ok(fileDto);
    }

    @Tag(name = "file")
    @Operation(summary = "User delete his file")
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<FileDto> deleteFile(@PathVariable Long fileId,
                                              Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[DELETE /file/delete/{}] Request received from userId={}", fileId, userId);

        fileService.isOwner(fileId, userId);
        log.info("[DELETE /file/delete/{}] Ownership validated for userId={}", fileId, userId);

        FileDto fileDto = fileService.delete(fileId);

        log.info("[DELETE /file/delete/{}] File deleted by userId={}", fileId, userId);

        return ResponseEntity.ok(fileDto);
    }
}

