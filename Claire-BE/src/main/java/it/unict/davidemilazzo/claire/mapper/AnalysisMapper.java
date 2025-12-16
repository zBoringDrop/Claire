package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.AnalysisDto;
import it.unict.davidemilazzo.claire.dto.UserDto;
import it.unict.davidemilazzo.claire.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface AnalysisMapper {
    AnalysisMapper INSTANCE = Mappers.getMapper(AnalysisMapper.class);

    @Mapping(source = "fileEntity.id", target = "fileId")
    @Mapping(source = "codeSnippetEntity.id", target = "codeSnippetId")
    @Mapping(source = "toolEntity.id", target = "toolId")
    @Mapping(source = "categories", target = "analysisCategoryIds")
    AnalysisDto toDto(AnalysisEntity analysisEntity);

    @Mapping(source = "fileId", target = "fileEntity")
    @Mapping(source = "codeSnippetId", target = "codeSnippetEntity")
    @Mapping(source = "toolId", target = "toolEntity")
    @Mapping(source = "analysisCategoryIds", target = "categories")
    AnalysisEntity toEntity(AnalysisDto analysisDto);

    List<AnalysisDto> toDtoList(List<AnalysisEntity> analysisEntities);

    default FileEntity mapFileIdToEntity(Long fileId) {
        if (fileId == null) return null;
        FileEntity file = new FileEntity();
        file.setId(fileId);
        return file;
    }

    default CodeSnippetEntity mapSnippetIdToEntity(Long snippetId) {
        if (snippetId == null) return null;
        CodeSnippetEntity snippet = new CodeSnippetEntity();
        snippet.setId(snippetId);
        return snippet;
    }

    default ToolEntity mapToolIdToEntity(Long toolId) {
        if (toolId == null) return null;
        ToolEntity tool = new ToolEntity();
        tool.setId(toolId);
        return tool;
    }

    default List<Long> mapCategoriesToIds(Set<AnalysisCategoryEntity> categories) {
        if (categories == null) return new ArrayList<>();
        return categories.stream()
                .map(AnalysisCategoryEntity::getId)
                .toList();
    }

    default Set<AnalysisCategoryEntity> mapIdsToCategories(List<Long> ids) {
        if (ids == null) return new HashSet<>();
        return ids.stream()
                .map(id -> {
                    AnalysisCategoryEntity category = new AnalysisCategoryEntity();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toSet());
    }
}
