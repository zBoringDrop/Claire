package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.AuthenticationResponseDto;
import it.unict.davidemilazzo.claire.dto.UserLoginDto;
import it.unict.davidemilazzo.claire.service.AuthenticationService;
import it.unict.davidemilazzo.claire.validation.DataValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user authentication")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final DataValidator<Object> dataValidator;
    private static final Logger log = LogManager.getLogger(AuthenticationController.class);

    @Tag(name = "auth")
    @Tag(name = "user login")
    @Operation(summary = "Authenticate a user and provide a jwt token")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody UserLoginDto userLoginDto) {

        log.info("[POST /login] Login request received for username={}", userLoginDto.getEmail());

        dataValidator.validateAndThrowException(userLoginDto);
        log.info("[POST /login] Input validation passed for username={}", userLoginDto.getEmail());

        AuthenticationResponseDto response = authenticationService.authenticate(userLoginDto);

        log.info("[POST /login] Login successful for username={}", userLoginDto.getEmail());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

