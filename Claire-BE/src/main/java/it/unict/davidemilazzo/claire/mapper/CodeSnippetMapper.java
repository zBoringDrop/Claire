package it.unict.davidemilazzo.claire.mapper;

import it.unict.davidemilazzo.claire.dto.CodeSnippetDto;
import it.unict.davidemilazzo.claire.dto.FileDto;
import it.unict.davidemilazzo.claire.model.CodeSnippetEntity;
import it.unict.davidemilazzo.claire.model.FileEntity;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguageEntity;
import it.unict.davidemilazzo.claire.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CodeSnippetMapper {
    CodeSnippetMapper INSTANCE = Mappers.getMapper(CodeSnippetMapper.class);

    @Mapping(source = "userEntity.id", target = "userId")
    @Mapping(source = "languageEntity.id", target = "programmingLanguageId")
    @Mapping(source = "codeText", target = "codeText")
    CodeSnippetDto toDto(CodeSnippetEntity codeSnippetEntity);

    @Mapping(source = "userId", target = "userEntity")
    @Mapping(source = "programmingLanguageId", target = "languageEntity")
    @Mapping(source = "codeText", target = "codeText")
    CodeSnippetEntity toEntity(CodeSnippetDto codeSnippetDto);

    List<CodeSnippetDto> toDtoList(List<CodeSnippetEntity> codeSnippetEntities);

    default UserEntity mapUserIdToEntity(Long userId) {
        if (userId == null) return null;
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }

    default ProgrammingLanguageEntity mapProgrammingLanguageIdToEntity(Long id) {
        if (id == null) return null;
        ProgrammingLanguageEntity lang = new ProgrammingLanguageEntity();
        lang.setId(id);
        return lang;
    }
}

