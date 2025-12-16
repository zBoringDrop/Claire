package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.CodeSnippetDto;
import it.unict.davidemilazzo.claire.dto.CodeSnippetPreviewDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.CodeSnippetService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/codesnippet")
@RequiredArgsConstructor
public class CodeSnippetController {

    private final CodeSnippetService codeSnippetService;
    private static final Logger log = LogManager.getLogger(CodeSnippetController.class);

    @Tag(name = "code snippet")
    @Operation(summary = "User upload code snippet")
    @PostMapping("/upload")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    public ResponseEntity<CodeSnippetDto> uploadCodeSnippet(@RequestBody CodeSnippetDto codeSnippetDto,
                                                            Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[POST /codesnippet/upload] Request received from userId={} title={}",
                userId, codeSnippetDto.getTitle());

        CodeSnippetDto codeSnippetDtoRes = codeSnippetService.uploadNew(codeSnippetDto, userId);

        log.info("[POST /codesnippet/upload] Upload completed for userId={} snippetId={}",
                userId, codeSnippetDtoRes.getId());

        return new ResponseEntity<>(codeSnippetDtoRes, HttpStatus.CREATED);
    }

    @Tag(name = "code snippet")
    @Operation(summary = "User get his code snippets list")
    @GetMapping("/list")
    public ResponseEntity<List<CodeSnippetDto>> listUserCodeSnippets(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /codesnippet/list] Request received from userId={}", userId);

        List<CodeSnippetDto> codeSnippetDtos = codeSnippetService.listUserCodeSnippet(userId);

        log.info("[GET /codesnippet/list] Returning {} snippets for userId={}",
                codeSnippetDtos.size(), userId);

        return ResponseEntity.ok(codeSnippetDtos);
    }

    @Tag(name = "code snippet")
    @Operation(summary = "User get his code snippets list")
    @GetMapping("/list/preview")
    public ResponseEntity<List<CodeSnippetPreviewDto>> listUserCodeSnippetsPreview(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /codesnippet/list/preview] Request received from userId={}", userId);

        List<CodeSnippetPreviewDto> codeSnippetDtos = codeSnippetService.listAllUserPreviews(userId);

        log.info("[GET /codesnippet/list/preview] Returning {} snippets for userId={}",
                codeSnippetDtos.size(), userId);

        return ResponseEntity.ok(codeSnippetDtos);
    }

    @Tag(name = "code snippet")
    @Operation(summary = "User get his code snippet")
    @GetMapping("/get/{codeSnippetId}")
    public ResponseEntity<CodeSnippetDto> getUserCodeSnippet(@PathVariable Long codeSnippetId,
                                                             Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /codesnippet/get/{}] Request received from userId={}", codeSnippetId, userId);

        codeSnippetService.isOwner(codeSnippetId, userId);
        log.info("[GET /codesnippet/get/{}] Ownership validated for userId={}", codeSnippetId, userId);

        CodeSnippetDto codeSnippetDto = codeSnippetService.findById(codeSnippetId);

        log.info("[GET /codesnippet/get/{}] Returning snippet for userId={}", codeSnippetId, userId);

        return ResponseEntity.ok(codeSnippetDto);
    }

    @Tag(name = "code snippet")
    @Operation(summary = "User get his code snippet preview")
    @GetMapping("/get/preview/{codeSnippetId}")
    public ResponseEntity<CodeSnippetPreviewDto> getUserCodeSnippetPreview(@PathVariable Long codeSnippetId,
                                                                            Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /codesnippet/get/preview/{}] Request received from userId={}", codeSnippetId, userId);

        codeSnippetService.isOwner(codeSnippetId, userId);
        log.info("[GET /codesnippet/get/preview/{}] Ownership validated for userId={}", codeSnippetId, userId);

        CodeSnippetPreviewDto codeSnippetDto = codeSnippetService.findPreviewById(codeSnippetId);

        log.info("[GET /codesnippet/get/preview/{}] Returning snippet for userId={}", codeSnippetId, userId);

        return ResponseEntity.ok(codeSnippetDto);
    }

    @Tag(name = "code snippet")
    @Operation(summary = "User delete his code snippet")
    @DeleteMapping("/delete/{codeSnippetId}")
    public ResponseEntity<CodeSnippetDto> deleteCodeSnippet(@PathVariable Long codeSnippetId,
                                                            Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[DELETE /codesnippet/delete/{}] Request received from userId={}", codeSnippetId, userId);

        codeSnippetService.isOwner(codeSnippetId, userId);
        log.info("[DELETE /codesnippet/delete/{}] Ownership validated for userId={}", codeSnippetId, userId);

        CodeSnippetDto codeSnippetDto = codeSnippetService.delete(codeSnippetId);

        log.info("[DELETE /codesnippet/delete/{}] Snippet deleted by userId={}", codeSnippetId, userId);

        return ResponseEntity.ok(codeSnippetDto);
    }
}
