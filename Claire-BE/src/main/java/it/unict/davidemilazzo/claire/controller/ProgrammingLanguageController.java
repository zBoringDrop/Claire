package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.ProgrammingLanguageDto;
import it.unict.davidemilazzo.claire.service.ProgrammingLanguageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/programming-languages")
public class ProgrammingLanguageController {

    private final ProgrammingLanguageService programmingLanguageService;
    private static final Logger log = LogManager.getLogger(ProgrammingLanguageController.class);

    @Tag(name = "programming languages")
    @Operation(summary = "Get all programming languages")
    @GetMapping("/all")
    ResponseEntity<List<ProgrammingLanguageDto>> getAll() {
        log.info("[GET /programming-languages/all] Request received: fetching all programming languages");

        List<ProgrammingLanguageDto> programmingLanguageDtos = programmingLanguageService.findAll();

        log.info("[GET /programming-languages/all] Returning {} programming languages", programmingLanguageDtos.size());

        return ResponseEntity.ok(programmingLanguageDtos);
    }

    @Tag(name = "programming languages")
    @Operation(summary = "Get programming language by id")
    @GetMapping("/{id}")
    ResponseEntity<ProgrammingLanguageDto> getById(@PathVariable Long id) {
        log.info("[GET /programming-languages/{}] Request received: get programming languages {}", id, id);

        ProgrammingLanguageDto programmingLanguageDto = programmingLanguageService.findById(id);

        log.info("[GET /programming-languages/{}] Returning programming language name '{}'", programmingLanguageDto.getId(), programmingLanguageDto.getName());

        return ResponseEntity.ok(programmingLanguageDto);
    }
}
