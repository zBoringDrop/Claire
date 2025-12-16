package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.dto.AIPreviewDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.AIService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tools/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;
    private static final Logger log = LogManager.getLogger(AIController.class);

    @Tag(name = "model")
    @Operation(summary = "Shows all registered and used AI models (locals and in cloud)")
    @GetMapping("/models")
    public ResponseEntity<List<AIDto>> showAllRegisteredModel() {

        log.info("[GET /models] Request received: fetching all registered AI models");

        List<AIDto> aiDtos = aiService.getAllRegistered();

        log.info("[GET /models] Returning {} models", aiDtos.size());

        return ResponseEntity.ok(aiDtos);
    }

    @Tag(name = "model")
    @Operation(summary = "Shows only all registered and installed AI models (locals and in cloud)")
    @GetMapping("/models/enabled")
    public ResponseEntity<List<AIDto>> showAllEnabledModel() {

        log.info("[GET /models/enabled] Request received: fetching enabled AI models");

        List<AIDto> aiEnabled = aiService.getAllEnabled();

        log.info("[GET /models/enabled] Returning {} enabled models", aiEnabled.size());

        return ResponseEntity.ok(aiEnabled);
    }

    @Tag(name = "model")
    @Operation(summary = "Get a specific installed AI models (locals and in cloud)")
    @GetMapping("/get/{aiId}")
    public ResponseEntity<AIDto> getModel(@PathVariable Long aiId) {

        log.info("[GET /get/{}] Request received: fetching model with id {}", aiId, aiId);

        AIDto aiDto = aiService.findById(aiId);

        log.info("[GET /get/{}] Found model: {}", aiId, aiDto);

        return ResponseEntity.ok(aiDto);
    }

    @Tag(name = "model")
    @Operation(summary = "Get a specific installed AI model with provider info (locals and in cloud)")
    @GetMapping("/get/preview/{aiId}")
    public ResponseEntity<AIPreviewDto> getSpecificModelPreview(@PathVariable Long aiId) {

        log.info("[GET /get/preview/{}] Request received: fetching model preview with id {}", aiId, aiId);

        AIPreviewDto aiPreviewDto = aiService.findAIPreviewById(aiId);

        log.info("[GET /get/preview/{}] Found model preview: {}", aiId, aiPreviewDto);

        return ResponseEntity.ok(aiPreviewDto);
    }

    @Tag(name = "model")
    @Operation(summary = "Get only user available AI models (all locals and in cloud with api)")
    @GetMapping("/available")
    public ResponseEntity<List<AIPreviewDto>> findAllAvailablePreviewForUser(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /available] Request received: fetching available models for user {}", userId);

        List<AIPreviewDto> aiPreviewDtos = aiService.findAllAvailablePreviewForUser(userId);

        log.info("[GET /available] Found {} models", aiPreviewDtos.size());

        return ResponseEntity.ok(aiPreviewDtos);
    }
}

