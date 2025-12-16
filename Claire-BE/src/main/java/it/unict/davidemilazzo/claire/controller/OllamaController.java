package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.service.AIService;
import it.unict.davidemilazzo.claire.service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ollama")
@RequiredArgsConstructor
public class OllamaController {

    private final OllamaService ollamaService;
    private final AIService aiService;
    private static final Logger log = LogManager.getLogger(OllamaController.class);

    @Tag(name = "ollama model")
    @Operation(summary = "Force database sync with registered and installed Ollama local models")
    @PostMapping("/models/sync")
    public ResponseEntity<String> forceSyncModels() {

        log.info("[POST /ollama/models/sync] Request received to force sync Ollama models");

        ollamaService.syncModelsWithDatabase(null);

        log.info("[POST /ollama/models/sync] Sync completed successfully");

        return ResponseEntity.ok("Done");
    }

    @Tag(name = "ollama model")
    @Operation(summary = "Get all registered and installed Ollama local models")
    @PostMapping("/models/show")
    public ResponseEntity<List<AIDto>> getAll() {

        log.info("[POST /ollama/models/show] Request received to list all Ollama models");

        List<AIDto> models = aiService.findByAiProviderName(AIProvidersEnum.OLLAMA);

        log.info("[POST /ollama/models/show] Returning {} Ollama models", models.size());

        return ResponseEntity.ok(models);
    }
}

