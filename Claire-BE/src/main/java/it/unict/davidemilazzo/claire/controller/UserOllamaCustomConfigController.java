package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.ai.ollama.UserOllamaCustomConfigDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.UserOllamaCustomConfigService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ollama/user/config")
@RequiredArgsConstructor
public class UserOllamaCustomConfigController {

    private final UserOllamaCustomConfigService userOllamaCustomConfigService;
    private static final Logger log = LogManager.getLogger(UserOllamaCustomConfigController.class);

    @Tag(name = "ollama custom config")
    @Operation(summary = "Create new configuration for a specific user")
    @PostMapping("/new")
    ResponseEntity<UserOllamaCustomConfigDto> createNew(@RequestBody UserOllamaCustomConfigDto configDto,
                                                        Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /ollama/user/config/new] Request received: create new Ollama custom config for user {}", configDto.getUserId());

        UserOllamaCustomConfigDto userConfigDto = userOllamaCustomConfigService.createNew(configDto, userId);

        log.info("[GET /ollama/user/config/new] Ollama custom config for user {} created: {}", configDto.getUserId(), userConfigDto.toString());

        return ResponseEntity.ok(userConfigDto);
    }

    @Tag(name = "ollama custom config")
    @Operation(summary = "Update the configuration for a specific user")
    @PostMapping("/update")
    ResponseEntity<UserOllamaCustomConfigDto> update(@RequestBody UserOllamaCustomConfigDto configDto,
                                                     Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /ollama/user/config/update] Request received: update the Ollama custom config of user {}", userId);

        UserOllamaCustomConfigDto userConfigDto = userOllamaCustomConfigService.update(configDto, userId);

        log.info("[GET /ollama/user/config/update] Ollama custom config for user {} updated", userId);

        return ResponseEntity.ok(userConfigDto);
    }

    @Tag(name = "ollama custom config")
    @Operation(summary = "Delete the configuration of a specific user")
    @DeleteMapping("/delete")
    ResponseEntity<UserOllamaCustomConfigDto> deleteByUserId(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /ollama/user/config/delete] Request received: delete the Ollama custom config of user {}", userId);

        UserOllamaCustomConfigDto userConfigDto = userOllamaCustomConfigService.deleteByUserId(userId);

        log.info("[GET /ollama/user/config/delete] Deleted Ollama custom config for user {}", userId);

        return ResponseEntity.ok(userConfigDto);
    }

    @Tag(name = "ollama custom config")
    @Operation(summary = "Get the configuration of a specific user")
    @GetMapping("/get")
    ResponseEntity<UserOllamaCustomConfigDto> findByUserId(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /ollama/user/config/get] Request received: get the Ollama custom config of user {}", userId);

        UserOllamaCustomConfigDto userConfigDto = userOllamaCustomConfigService.findByUserId(userId);

        log.info("[GET /ollama/user/config/get] Returning Ollama custom config of user {}", userId);

        return ResponseEntity.ok(userConfigDto);
    }

    @Tag(name = "ollama custom config")
    @Operation(summary = "Verify if the configuration for a specific user exists")
    @GetMapping("/exists")
    ResponseEntity<Boolean> existsByUserId(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /ollama/user/config/exists] Request received: verify if the Ollama custom config of user {} exists", userId);

        Boolean exists = userOllamaCustomConfigService.existsByUserId(userId);

        log.info("[GET /ollama/user/config/exists] Returning Ollama custom config of user {}", userId);

        return ResponseEntity.ok(exists);
    }


}
