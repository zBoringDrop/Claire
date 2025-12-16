package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.ProgrammingLanguageDto;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProgrammingLanguageMapper {
    ProgrammingLanguageMapper MAPPER = Mappers.getMapper(ProgrammingLanguageMapper.class);

    ProgrammingLanguageDto toDto(ProgrammingLanguageEntity programmingLanguageEntity);
    List<ProgrammingLanguageDto> toDtoList(List<ProgrammingLanguageEntity> programmingLanguageEntities);
    ProgrammingLanguageEntity toEntity(ProgrammingLanguageDto programmingLanguageDto);
    List<ProgrammingLanguageEntity> toEntitList(List<ProgrammingLanguageDto> programmingLanguageDtos);
}
