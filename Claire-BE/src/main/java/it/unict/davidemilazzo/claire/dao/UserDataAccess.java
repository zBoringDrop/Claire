package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.UserDto;
import it.unict.davidemilazzo.claire.dto.UserRegistrationDto;
import it.unict.davidemilazzo.claire.exception.EmailNotFoundException;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.exception.UsernameAlreadyRegisteredException;
import it.unict.davidemilazzo.claire.mapper.UserMapper;
import it.unict.davidemilazzo.claire.mapper.UserRegistrationMapper;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.respository.UserEntityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository("user-mysql")
public class UserDataAccess implements UserDao {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDto createNew(UserRegistrationDto userRegistrationDto) {
        UserEntity userEntity = UserRegistrationMapper.INSTANCE.toEntity(userRegistrationDto);
        return UserMapper.INSTANCE.toDto(userEntityRepository.save(userEntity));
    }

    @Override
    public UserDto update(UserDto userDto) {
        UserEntity userEntityToUpdate = userEntityRepository.findById(userDto.getId())
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        userEntityToUpdate.setRole(userDto.getRole());
        userEntityToUpdate.setSurname(userDto.getSurname());
        userEntityToUpdate.setName(userDto.getName());
        userEntityToUpdate.setNickname(userDto.getNickname());
        return UserMapper.INSTANCE.toDto(userEntityRepository.save(userEntityToUpdate));
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntityToDelete = userEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        userEntityRepository.delete(userEntityToDelete);
    }

    @Override
    public UserDto findById(Long id) {
        UserEntity userEntity = userEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return UserMapper.INSTANCE.toDto(userEntity);
    }

    @Override
    public List<UserDto> findAllNoPage() {
        List<UserEntity> userEntityList = userEntityRepository.findAll();
        return UserMapper.INSTANCE.toDtoList(userEntityList);
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(ExceptionMessages.EMAIL_NOT_FOUND));
        return UserMapper.INSTANCE.toDto(userEntity);
    }

    @Override
    public UserDto findByNickname(String nickname) {
        UserEntity userEntity = userEntityRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameAlreadyRegisteredException(ExceptionMessages.USERNAME_ALREADY_REGISTERED));
        return UserMapper.INSTANCE.toDto(userEntity);
    }

    @Override
    public Long countAll() {
        return userEntityRepository.count();
    }

    @Override
    public boolean exists(Long id) {
        return userEntityRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userEntityRepository.existsByNickname(nickname);
    }
}
