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
@RequestMapping("api/analysis/codesnippet")
@RequiredArgsConstructor
public class CodeSnippetAnalysisController {

    private final AnalysisService analysisService;
    private static final Logger log = LogManager.getLogger(CodeSnippetAnalysisController.class);

    @Tag(name = "code snippet analysis")
    @Operation(summary = "Shows all the user's personal analyses performed on all code snippets")
    @GetMapping("/show/all")
    public ResponseEntity<List<AnalysisDto>> showUserCodeSnippetAnalysis(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /codesnippet/show/all] Request received from userId={}", userId);

        List<AnalysisDto> list = analysisService.getAllUserCodeSnippetAnalysis(userId);

        log.info("[GET /codesnippet/show/all] Returning {} code snippet analyses for userId={}",
                list.size(), userId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Tag(name = "code snippet analysis")
    @Operation(summary = "Shows all the user's personal analyses performed on a specific code snippet")
    @GetMapping("/show/{codeSnippetId}")
    public ResponseEntity<List<AnalysisDto>> getUserCodeSnippetAnalysis(
            @PathVariable Long codeSnippetId,
            Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /codesnippet/show/{}] Request received from userId={}", codeSnippetId, userId);

        List<AnalysisDto> list =
                analysisService.getUserCodeSnippetAnalysis(userId, codeSnippetId);

        log.info("[GET /codesnippet/show/{}] Returning {} analyses for userId={}",
                codeSnippetId, list.size(), userId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Tag(name = "code snippet analysis")
    @Operation(summary = "Start a code snippet analysis using a specific AI model")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    @PostMapping("/analyze")
    public ResponseEntity<AnalysisDto> analyzeCodeSnippet(@RequestBody AnalysisRequestDto analysisRequestDto,
                                                          Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[POST /codesnippet/analyze] Request received from userId={} for snippetId={}",
                userId, analysisRequestDto.getSourceId());

        AnalysisDto analysisDto = analysisService.asyncAnalyzeCodeSnippet(analysisRequestDto, userId);

        log.info("[POST /codesnippet/analyze] Analysis started: analysisId={} userId={} snippetId={}",
                analysisDto.getId(), userId, analysisRequestDto.getSourceId());

        return ResponseEntity.ok(analysisDto);
    }
}

