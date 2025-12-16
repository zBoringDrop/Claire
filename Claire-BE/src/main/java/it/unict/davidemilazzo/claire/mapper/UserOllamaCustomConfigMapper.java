package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.ai.ollama.UserOllamaCustomConfigDto;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.model.UserOllamaCustomConfigEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserOllamaCustomConfigMapper {

    UserOllamaCustomConfigMapper INSTANCE = Mappers.getMapper(UserOllamaCustomConfigMapper.class);

    @Mapping(source = "user.id", target = "userId")
    UserOllamaCustomConfigDto toDto(UserOllamaCustomConfigEntity entity);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "id", ignore = true)
    UserOllamaCustomConfigEntity toEntity(UserOllamaCustomConfigDto dto);

    default UserEntity mapUserIdToEntity(Long userId) {
        if (userId == null) return null;
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }
}