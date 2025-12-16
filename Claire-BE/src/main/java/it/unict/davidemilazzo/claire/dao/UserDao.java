package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.UserDto;
import it.unict.davidemilazzo.claire.dto.UserRegistrationDto;

import java.util.List;

public interface UserDao {
    UserDto createNew(UserRegistrationDto userRegistrationDto);
    UserDto update(UserDto userDto);
    void delete(Long id);
    UserDto findById(Long id);
    List<UserDto> findAllNoPage();
    UserDto findByEmail(String email);
    UserDto findByNickname(String nickname);
    Long countAll();
    boolean exists(Long id);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
