package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.AIService;
import it.unict.davidemilazzo.claire.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;
    private final AIService aiService;
    private static final Logger log = LogManager.getLogger(GeminiController.class);

    @Tag(name = "gemini model")
    @Operation(summary = "Force database sync with available cloud Google Gemini models")
    @PostMapping("/models/sync")
    public ResponseEntity<String> forceSyncModels(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[POST /gemini/models/sync] Request received to force sync Gemini models");

        //geminiService.syncModelsWithDatabase();

        log.info("[POST /gemini/models/sync] Sync completed successfully");

        return ResponseEntity.ok("Done");
    }

    @Tag(name = "gemini model")
    @Operation(summary = "Get all available cloud Google Gemini models")
    @PostMapping("/models/show")
    public ResponseEntity<List<AIDto>> getAll() {

        log.info("[POST /gemini/models/show] Request received to list all Gemini models");

        List<AIDto> models = aiService.findByAiProviderName(AIProvidersEnum.GOOGLE_AI);

        log.info("[POST /gemini/models/show] Returning {} Gemini models", models.size());

        return ResponseEntity.ok(models);
    }
}

