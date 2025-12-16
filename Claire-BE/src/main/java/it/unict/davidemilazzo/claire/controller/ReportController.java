package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.ReportDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Tag(name = "[NOT USED YET]")   //TODO Complete
    @Tag(name = "code analysis report")
    @Operation(summary = "Shows the user's personal report of an analysis")
    @GetMapping("/download/{analysisId}")
    public ResponseEntity<ReportDto> getUserAnalysisReport(@PathVariable Long analysisId,
                                                           Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();
        return ResponseEntity.ok(reportService.getUserAnalysisReport(userId, analysisId));
    }
}
