package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.model.AIProviderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AIProviderMapper {
    AIProviderMapper MAPPER = Mappers.getMapper(AIProviderMapper.class);

    AIProviderEntity toEntity(AIProviderDto aiProviderDto);
    AIProviderDto toDto(AIProviderEntity aiProvider);
    List<AIProviderDto> toDtoList(List<AIProviderEntity> aiProviderEntities);
}
