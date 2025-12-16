package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.service.AIProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/ai/providers")
public class AIProviderController {

    private final AIProviderService aiProviderService;

    @Tag(name = "ai providers")
    @Operation(summary = "Get all ai providers")
    @GetMapping("/all")
    ResponseEntity<List<AIProviderDto>> getAll() {
        return ResponseEntity.ok(aiProviderService.findAll());
    }
}
