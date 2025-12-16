package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.AnalysisDto;
import it.unict.davidemilazzo.claire.dto.AnalysisRequestDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analysis/file")
@RequiredArgsConstructor
public class FileAnalysisController {

    private final AnalysisService analysisService;
    private static final Logger log = LogManager.getLogger(FileAnalysisController.class);

    @Tag(name = "file analysis")
    @Operation(summary = "Shows all the user's personal analyses performed on all files")
    @GetMapping("/show/all")
    public ResponseEntity<List<AnalysisDto>> showUserFileAnalysis(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /fileanalysis/show/all] Request received from userId={}", userId);

        List<AnalysisDto> list = analysisService.getAllUserFileAnalysis(userId);

        log.info("[GET /fileanalysis/show/all] Returning {} file analyses for userId={}", list.size(), userId);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "file analysis")
    @Operation(summary = "Shows all the user's personal analyses performed on a specific file")
    @GetMapping("/show/{fileId}")
    public ResponseEntity<List<AnalysisDto>> getUserFileAnalysis(
            @PathVariable Long fileId,
            Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /fileanalysis/show/{}] Request received from userId={}", fileId, userId);

        List<AnalysisDto> list = analysisService.getUserFileAnalysis(userId, fileId);

        log.info("[GET /fileanalysis/show/{}] Returning {} analyses for userId={}", fileId, list.size(), userId);

        return ResponseEntity.ok(list);
    }

    @Tag(name = "file analysis")
    @Operation(summary = "Start a file analysis using a specific AI model")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    @PostMapping("/analyze")
    public ResponseEntity<AnalysisDto> analyzeFile(
            @RequestBody AnalysisRequestDto analysisRequestDto,
            Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[POST /fileanalysis/analyze] Request received from userId={} for fileId={}",
                userId, analysisRequestDto.getSourceId());

        AnalysisDto analysisDto = analysisService.asyncAnalyzeFile(analysisRequestDto, userId);

        log.info("[POST /fileanalysis/analyze] Analysis started: analysisId={} userId={} fileId={}",
                analysisDto.getId(), userId, analysisRequestDto.getSourceId());

        return ResponseEntity.ok(analysisDto);
    }
}

