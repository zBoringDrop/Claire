package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.UserRegistrationDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRegistrationMapper {
    UserRegistrationMapper INSTANCE = Mappers.getMapper(UserRegistrationMapper.class);

    UserRegistrationDto toDto(UserEntity userEntity);
    UserEntity toEntity(UserRegistrationDto userRegistrationDto);
}
