package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.AnalysisCategoryDto;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface AnalysisCategoryMapper {
    AnalysisCategoryMapper INSTANCE = Mappers.getMapper(AnalysisCategoryMapper.class);

    AnalysisCategoryDto toDto(AnalysisCategoryEntity analysisCategory);
    AnalysisCategoryEntity toEntity(AnalysisCategoryDto analysisCategoryDto);
    List<AnalysisCategoryDto> toDtoList(List<AnalysisCategoryEntity> entities);
    Set<AnalysisCategoryDto> toDtoSet(Set<AnalysisCategoryEntity> entities);
}
