package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.GenericResponseDto;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyDto;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.UserProviderApiKeyService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/provider/key")
@RequiredArgsConstructor
public class UserProviderApiKeyController {

    private final UserProviderApiKeyService userProviderApiKeyService;
    private static final Logger log = LogManager.getLogger(UserProviderApiKeyController.class);

    @Tag(name = "User models api keys")
    @Operation(summary = "Get all available AI providers and the user associated key")
    @GetMapping("/all")
    ResponseEntity<List<UserProviderApiKeyPreviewDto>> getAll(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /all] Request received: fetching all providers and keys for user {}", userId);

        List<UserProviderApiKeyPreviewDto> userProviderApiKeyPreviewDtos = userProviderApiKeyService.findProvidersWithUserKeysDecrypted(userId);

        log.info("[GET /all] Returning {} providers and keys for user {}", userProviderApiKeyPreviewDtos.size(), userId);

        return ResponseEntity.ok(userProviderApiKeyPreviewDtos);
    }

    @Tag(name = "User models api keys")
    @Operation(summary = "Add/register a new specific user AI provider key")
    @PostMapping("/add/key")
    ResponseEntity<GenericResponseDto> createNew(@RequestBody UserProviderApiKeyDto userProviderApiKeyDto) {

        log.info("[POST /add/key] Request received: creating new provider {} key for user {}", userProviderApiKeyDto.getProviderId(), userProviderApiKeyDto.getUserId());

        Long id = userProviderApiKeyService.createNew(userProviderApiKeyDto).getId();

        log.info("[POST /add/key] User {} registered new provider {} api key", userProviderApiKeyDto.getUserId(), userProviderApiKeyDto.getProviderId());

        return ResponseEntity.ok(new GenericResponseDto(id, "OK"));
    }

    @Tag(name = "User models api keys")
    @Operation(summary = "Update a specific user AI provider key")
    @PatchMapping("/update")
    ResponseEntity<GenericResponseDto> updateKey(@RequestBody UserProviderApiKeyDto userProviderApiKeyDto) {

        log.info("[PATCH /update] Request received: updating provider {} key of user {}", userProviderApiKeyDto.getProviderId(), userProviderApiKeyDto.getUserId());

        Long id = userProviderApiKeyService.update(userProviderApiKeyDto).getId();

        log.info("[PATCH /update] User {} provider {} api key updated", userProviderApiKeyDto.getUserId(), userProviderApiKeyDto.getProviderId());

        return ResponseEntity.ok(new GenericResponseDto(id, "OK"));
    }

    @Tag(name = "User models api keys")
    @Operation(summary = "Delete a specific user AI provider key")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<GenericResponseDto> deleteKey(@PathVariable Long id,
                                                 Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[DELETE /delete/{}] Request received: delete provider api key row {} from user {}", id, id, userId);

        Long deletedId = userProviderApiKeyService.delete(id).getId();

        log.info("[DELETE /delete/{}] User {} deleted provider api key row {}", id, userId, id);

        return ResponseEntity.ok(new GenericResponseDto(deletedId, "OK"));
    }

    @Tag(name = "User models api keys")
    @Operation(summary = "Verify if an user AI provider key exists")
    @GetMapping("/exists/provider")
    ResponseEntity<Boolean> existsByUserIdProviderId(@RequestParam Long userId,
                                                     @RequestParam Long providerId) {

        log.info("[GET /exists/provider] Request received: checking if user {} has an api key for provider {}", userId, providerId);

        boolean hasKey = userProviderApiKeyService.existsByUserIdAndProviderId(userId, providerId);

        log.info("[GET /exists/provider] User {} has an api key for provider {}: {}", userId, providerId, hasKey);

        return ResponseEntity.ok(hasKey);
    }

}
