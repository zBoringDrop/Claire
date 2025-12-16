package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.config.JwtUtil;
import it.unict.davidemilazzo.claire.dto.AuthenticationResponseDto;
import it.unict.davidemilazzo.claire.dto.UserLoginDto;
import it.unict.davidemilazzo.claire.exception.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private static final Logger log = LogManager.getLogger(AuthenticationService.class);

    public AuthenticationResponseDto authenticate(UserLoginDto userLoginDto) {
        log.info("Received login request for user {}", userLoginDto.getEmail());
        if (!userService.existsByEmail(userLoginDto.getEmail())) {
            throw new EmailNotFoundException(ExceptionMessages.EMAIL_NOT_FOUND);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException(ExceptionMessages.AUTHENTICATION_ERROR);
        }

        log.info("User {} logged successfully", userLoginDto.getEmail());
        return new AuthenticationResponseDto(jwtUtil.generateToken(userLoginDto.getEmail()));
    }

}
