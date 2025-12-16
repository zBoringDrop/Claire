package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.UserDao;
import it.unict.davidemilazzo.claire.dto.UserDto;
import it.unict.davidemilazzo.claire.dto.UserRegistrationDto;
import it.unict.davidemilazzo.claire.exception.EmailAlreadyRegisteredException;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.exception.UsernameAlreadyRegisteredException;
import it.unict.davidemilazzo.claire.model.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LogManager.getLogger(UserService.class);

    @Transactional
    public UserDto createNew(UserRegistrationDto userRegistrationDto) {
        log.info("Received registration request for user {}", userRegistrationDto);
        userAlreadyExists(userRegistrationDto);

        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRegistrationDto.setRole(Role.USER);

        UserDto userDto = userDao.createNew(userRegistrationDto);
        log.info("User {} ({}) registered with id {}", userDto.getNickname(), userDto.getEmail(), userDto.getId());
        return userDto;
    }

    public void userAlreadyExists(UserRegistrationDto userRegistrationDto) {
        if (userDao.existsByEmail(userRegistrationDto.getEmail())) {
            throw new EmailAlreadyRegisteredException(ExceptionMessages.EMAIL_ALREADY_REGISTERED);
        }
        if (userDao.existsByNickname(userRegistrationDto.getNickname())) {
            throw new UsernameAlreadyRegisteredException(ExceptionMessages.USERNAME_ALREADY_REGISTERED);
        }
    }

    public UserDto findById(Long id) {
        return userDao.findById(id);
    }

    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    public boolean existsByNickname(String username) {
        return userDao.existsByNickname(username);
    }

    @Transactional
    public UserDto update(UserDto updatedUserDto, Long userId) {
        if (!userDao.exists(userId)) {
            throw new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND);
        }

        updatedUserDto.setId(userId);
        updatedUserDto.setRole(Role.USER);
        return userDao.update(updatedUserDto);
    }

    public boolean exists(Long id) {
        return userDao.exists(id);
    }
}
