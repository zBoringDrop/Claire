package it.unict.davidemilazzo.claire.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unict.davidemilazzo.claire.dto.UserDto;
import it.unict.davidemilazzo.claire.dto.UserRegistrationDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.service.UserService;
import it.unict.davidemilazzo.claire.validation.DataValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DataValidator<Object> dataValidator;
    private static final Logger log = LogManager.getLogger(UserController.class);

    @Tag(name = "auth")
    @Tag(name = "user registration")
    @Operation(summary = "Register a new user in the database")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegistrationDto userRegistrationDto) {

        log.info("[POST /user/register] Registration request received for username={}", userRegistrationDto.getNickname());

        dataValidator.validateAndThrowException(userRegistrationDto);

        UserDto createdUser = userService.createNew(userRegistrationDto);

        log.info("[POST /user/register] User registered successfully with id={}", createdUser.getId());

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Tag(name = "user profile")
    @Operation(summary = "User get information about his profile")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserProfile(Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[GET /user/me] Profile request received for userId={}", userId);

        UserDto userDto = userService.findById(userId);

        log.info("[GET /user/me] Returning profile for userId={}", userId);

        return ResponseEntity.ok(userDto);
    }

    @Tag(name = "user profile")
    @Operation(summary = "User update his profile (no email and password)")
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto userDto,
                                                 Authentication authentication) {

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Long userId = userEntity.getId();

        log.info("[PUT /user/update] Update profile request received for userId={}", userId);

        UserDto updatedUser = userService.update(userDto, userId);

        log.info("[PUT /user/update] Profile updated successfully for userId={}", userId);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}

