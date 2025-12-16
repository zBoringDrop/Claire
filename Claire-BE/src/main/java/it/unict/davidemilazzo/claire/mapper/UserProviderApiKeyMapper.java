package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyDto;
import it.unict.davidemilazzo.claire.model.AIProviderEntity;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.model.UserProviderApiKeyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserProviderApiKeyMapper {

    UserProviderApiKeyMapper INSTANCE = Mappers.getMapper(UserProviderApiKeyMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "providerId", source = "provider.id")
    UserProviderApiKeyDto toDto(UserProviderApiKeyEntity entity);

    List<UserProviderApiKeyDto> toDtoList(List<UserProviderApiKeyEntity> entities);

    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    @Mapping(target = "provider", source = "providerId", qualifiedByName = "idToProvider")
    UserProviderApiKeyEntity toEntity(UserProviderApiKeyDto dto);

    List<UserProviderApiKeyEntity> toEntityList(List<UserProviderApiKeyDto> dtos);

    @Named("idToUser")
    default UserEntity idToUser(Long id) {
        if (id == null) return null;
        UserEntity u = new UserEntity();
        u.setId(id);
        return u;
    }

    @Named("idToProvider")
    default AIProviderEntity idToProvider(Long id) {
        if (id == null) return null;
        AIProviderEntity p = new AIProviderEntity();
        p.setId(id);
        return p;
    }
}
