package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.StaticToolDto;
import it.unict.davidemilazzo.claire.service.StaticToolService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tools/static")
@RequiredArgsConstructor
public class StaticToolController {

    private final StaticToolService staticToolService;
    private static final Logger log = LogManager.getLogger(StaticToolController.class);

    @PostMapping("/new")
    public ResponseEntity<StaticToolDto> createNew(@RequestBody StaticToolDto staticToolDto) {

        log.info("[POST /statics/new] Request received to create new static tool name={}", staticToolDto.getName());

        StaticToolDto created = staticToolService.createNew(staticToolDto);

        log.info("[POST /statics/new] Static tool created with id={}", created.getId());

        return ResponseEntity.ok(created);
    }

    @Tag(name = "tools")
    @Operation(summary = "Shows all registered and used static analysis tools")
    @GetMapping("/all")
    public ResponseEntity<List<StaticToolDto>> showAll() {

        log.info("[GET /statics/all] Request received to list all static tools");

        List<StaticToolDto> staticToolDtos = staticToolService.getAll();

        log.info("[GET /statics/all] Returning {} static tools", staticToolDtos.size());

        return ResponseEntity.ok(staticToolDtos);
    }
}
