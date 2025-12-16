package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.dto.GenericResponseDto;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto;
import it.unict.davidemilazzo.claire.model.AIType;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.AIModelSynchroniserService;
import it.unict.davidemilazzo.claire.service.AIProviderService;
import it.unict.davidemilazzo.claire.service.UserProviderApiKeyService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/synchroniser")
public class AIModelSynchroniserController {

    private final AIModelSynchroniserService aiModelSynchroniserService;
    private final AIProviderService aiProviderService;
    private final UserProviderApiKeyService userProviderApiKeyService;
    private static final Logger log = LogManager.getLogger(AIModelSynchroniserController.class);

    @Tag(name = "model")
    @Operation(summary = "Synchronise all models belonging to the specified provider name")
    @PostMapping("/sync/name/{provider}")
    public ResponseEntity<GenericResponseDto> syncProviderName(@PathVariable AIProvidersEnum provider,
                                                                Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        AIProviderDto aiProviderDto = aiProviderService.findByName(provider);

        String apiKey = null;

        if (aiProviderDto.getAiType() == AIType.CLOUD) {
            apiKey = userProviderApiKeyService.findByUserAndProviderIdDecrypted(userId, aiProviderDto.getId()).getApiKey();
        }

        log.info("[POST /sync/name/{}] Request received: model synchronisation for provider {} from user {}", provider, aiProviderDto.getId(), userId);

        aiModelSynchroniserService.syncProvider(provider, apiKey);

        log.info("[POST /sync/name/{}] Models synchronisation for provider {} from user {} completed", provider, aiProviderDto.getId(), userId);

        return ResponseEntity.ok(new GenericResponseDto(1L, "OK"));
    }

    @Tag(name = "model")
    @Operation(summary = "Synchronise all models belonging to the specified provider id")
    @PostMapping("/sync/id/{provider}")
    public ResponseEntity<GenericResponseDto> syncProviderId(@PathVariable Long provider,
                                                               Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        AIProviderDto aiProviderDto = aiProviderService.findById(provider);

        String apiKey = null;

        if (aiProviderDto.getAiType() == AIType.CLOUD) {
            apiKey = userProviderApiKeyService.findByUserAndProviderIdDecrypted(userId, aiProviderDto.getId()).getApiKey();
        }

        log.info("[POST /sync/id/{}] Request received: model synchronisation for provider {} from user {}", provider, aiProviderDto.getName(), userId);

        aiModelSynchroniserService.syncProvider(aiProviderDto.getName(), apiKey);

        log.info("[POST /sync/id/{}] Models synchronisation for provider {} from user {} completed", provider, aiProviderDto.getName(), userId);

        return ResponseEntity.ok(new GenericResponseDto(1L, "OK"));
    }

    @Tag(name = "model")
    @Operation(summary = "Synchronise all models")
    @PostMapping("/sync/all")
    public ResponseEntity<GenericResponseDto> syncAllProviders(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[POST /sync/all] Request received: model synchronisation for all providers from user {}", userId);

        List<UserProviderApiKeyPreviewDto> userKeysDecrypted = userProviderApiKeyService.findProvidersWithUserKeysDecrypted(userId);

        Map<AIProvidersEnum, String> userProvidersAndKeys = new HashMap<>();
        for (UserProviderApiKeyPreviewDto userProviderAndKey : userKeysDecrypted) {
            userProvidersAndKeys.put(userProviderAndKey.getProviderName(),
                    userProviderAndKey.getApiKey());
        }
        //log.info("[POST /sync/all] Request received: userProvidersAndKeys map all providers from user {}: {}", userId, userProvidersAndKeys);

        aiModelSynchroniserService.syncAll(userProvidersAndKeys);

        log.info("[POST /sync/all] Models synchronisation for all providers from user {} completed", userId);

        return ResponseEntity.ok(new GenericResponseDto(1L, "OK"));
    }
}
