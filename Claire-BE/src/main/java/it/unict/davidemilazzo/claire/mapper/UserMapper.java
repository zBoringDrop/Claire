package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.UserDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(UserEntity userEntity);
    UserEntity toEntity(UserDto userDto);
    List<UserDto> toDtoList(List<UserEntity> userEntities);
}
