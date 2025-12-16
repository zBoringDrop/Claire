package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.AnalysisDto;
import it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto;
import it.unict.davidemilazzo.claire.model.AnalysisStatus;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;
    private static final Logger log = LogManager.getLogger(AnalysisController.class);

    @Tag(name = "analysis")
    @Operation(summary = "Get all user analysis")
    @GetMapping("/all")
    ResponseEntity<List<AnalysisDto>> getAll(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /analysis/all] Request received from userId={}", userId);

        List<AnalysisDto> list = analysisService.getAllUserAnalysis(userId);

        log.info("[GET /analysis/all] Returning {} analyses for userId={}", list.size(), userId);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "analysis")
    @Operation(summary = "Get a specific user analysis")
    @GetMapping("/{analysisId}")
    ResponseEntity<AnalysisDto> getAnalysis(@PathVariable Long analysisId,
                                            Authentication authentication) {

        log.info("[GET /analysis/{}] Request received", analysisId);

        AnalysisDto dto = analysisService.findById(analysisId);

        log.info("[GET /analysis/{}] Returning analysis: {}", analysisId, analysisId);

        return ResponseEntity.ok(dto);
    }

    @Tag(name = "analysis")
    @Operation(summary = "Delete a specific analysis")
    @DeleteMapping("/delete/{analysisId}")
    ResponseEntity<AnalysisDto> deleteAnalysis(@PathVariable Long analysisId,
                                               Authentication authentication) {

        log.info("[DELETE /analysis/delete/{}] Request received", analysisId);

        AnalysisDto deleted = analysisService.delete(analysisId);

        log.info("[DELETE /analysis/delete/{}] Analysis deleted", analysisId);

        return ResponseEntity.ok(deleted);
    }

    @Tag(name = "analysis")
    @Operation(summary = "Get all user analysis by a specific status")
    @GetMapping("/status/{status}")
    ResponseEntity<List<AnalysisDto>> getByStatus(@PathVariable AnalysisStatus status,
                                                  Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /analysis/status/{}] Request received from userId={}", status, userId);

        List<AnalysisDto> list = analysisService.getUserAnalysisByStatus(userId, status);

        log.info("[GET /analysis/status/{}] Returning {} analyses for userId={}",
                status, list.size(), userId);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "analysis")
    @Operation(summary = "Get all user analysis by a specific status")
    @GetMapping("/running")
    ResponseEntity<List<AnalysisPreviewDto>> getRunning(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /analysis/running] Request received from userId={}", userId);

        List<AnalysisPreviewDto> list = analysisService.findAllPreviewsByUserAndStates(userId, List.of(AnalysisStatus.VALIDATING, AnalysisStatus.IN_PROGRESS));

        log.info("[GET /analysis/running] Returning {} running analyses for userId={}",
                list.size(), userId);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "analysis")
    @Tag(name = "preview")
    @Operation(summary = "Get all user analysis previews")
    @GetMapping("/previews")
    ResponseEntity<List<AnalysisPreviewDto>> getAllPreviews(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /analysis/previews] Request received from userId={}", userId);

        List<AnalysisPreviewDto> list = analysisService.getAllUserPreviews(userId);

        log.info("[GET /analysis/previews] Returning {} previews for userId={}",
                list.size(), userId);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "analysis")
    @Tag(name = "preview")
    @Operation(summary = "Get all analysis previews by file and user id")
    @GetMapping("/file/{fileId}/previews")
    ResponseEntity<List<AnalysisPreviewDto>> getPreviewsByUserAndFileId(@PathVariable Long fileId,
                                                                        Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /analysis/file/{}/previews] Request received from userId={}", fileId, userId);

        List<AnalysisPreviewDto> list = analysisService.findAllPreviewsByUserAndFileId(userId, fileId);

        log.info("[GET /analysis/file/{}/previews] Returning {} previews for userId={}", fileId, list.size(), userId);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "analysis")
    @Tag(name = "preview")
    @Operation(summary = "Get all analysis previews by snippet and user id")
    @GetMapping("/snippet/{snippetId}/previews")
    ResponseEntity<List<AnalysisPreviewDto>> getPreviewsByUserAndSnippetId(@PathVariable Long snippetId,
                                                                        Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /analysis/snippet/{}/previews] Request received from userId={}", snippetId, userId);

        List<AnalysisPreviewDto> list = analysisService.findAllPreviewsByUserAndSnippetId(userId, snippetId);

        log.info("[GET /analysis/snippet/{}/previews] Returning {} previews for userId={}", snippetId, list.size(), userId);

        return ResponseEntity.ok(list);
    }
}

