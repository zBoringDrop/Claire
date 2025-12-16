package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.dto.StaticToolDto;
import it.unict.davidemilazzo.claire.dto.ToolDto;
import it.unict.davidemilazzo.claire.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface ToolMapper {

    ToolMapper INSTANCE = Mappers.getMapper(ToolMapper.class);

    ToolDto toDto(ToolEntity entity);
    ToolEntity toEntity(ToolDto dto);

    @Mapping(target = "aiProviderId", source = "aiProviderEntity.id")
    @Mapping(target = "description", source = "description")
    AIDto toDto(AIEntity entity);

    @Mapping(target = "aiProviderEntity", source = "aiProviderId", qualifiedByName = "idToProvider")
    AIEntity toEntity(AIDto dto);

    List<AIDto> toDtoListAI(List<AIEntity> entities);
    List<AIEntity> toEntityListAI(List<AIDto> dtos);

    @Mapping(target = "supportedLanguageIds", source = "supportedLanguages", qualifiedByName = "languagesToIds")
    StaticToolDto toDto(StaticToolEntity entity);

    @Mapping(target = "supportedLanguages", source = "supportedLanguageIds", qualifiedByName = "idsToLanguages")
    StaticToolEntity toEntity(StaticToolDto dto);

    List<StaticToolDto> toDtoListStatic(List<StaticToolEntity> entities);
    List<StaticToolEntity> toEntityListStatic(List<StaticToolDto> dtos);

    @Named("languagesToIds")
    default Set<Long> languagesToIds(Set<ProgrammingLanguageEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(ProgrammingLanguageEntity::getId)
                .collect(Collectors.toSet());
    }

    @Named("idsToLanguages")
    default Set<ProgrammingLanguageEntity> idsToLanguages(Set<Long> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> ProgrammingLanguageEntity.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    @Named("idToProvider")
    default AIProviderEntity idToProvider(Long id) {
        if (id == null) return null;
        return AIProviderEntity.builder().id(id).build();
    }
}
