package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.ReportDto;
import it.unict.davidemilazzo.claire.model.AnalysisEntity;
import it.unict.davidemilazzo.claire.model.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(source = "analysisEntity.id", target = "analysisId")
    ReportDto toDto(ReportEntity reportEntity);

    @Mapping(source = "analysisId", target = "analysisEntity")
    ReportEntity toEntity(ReportDto reportDto);

    List<ReportDto> toDtoList(List<ReportEntity> reportEntities);

    default AnalysisEntity mapAnalysisIdToEntity(Long analysisId) {
        if (analysisId == null) return null;
        AnalysisEntity analysisEntity = new AnalysisEntity();
        analysisEntity.setId(analysisId);
        return analysisEntity;
    }
}
