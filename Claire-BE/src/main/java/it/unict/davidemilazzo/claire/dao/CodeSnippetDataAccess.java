package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.CodeSnippetDto;
import it.unict.davidemilazzo.claire.dto.CodeSnippetPreviewDto;
import it.unict.davidemilazzo.claire.exception.CodeSnippetEmptyException;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.CodeSnippetMapper;
import it.unict.davidemilazzo.claire.model.CodeSnippetEntity;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import it.unict.davidemilazzo.claire.respository.CodeSnippetEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("codesnippet-mysql")
@RequiredArgsConstructor
public class CodeSnippetDataAccess implements CodeSnippetDao {

    private final CodeSnippetEntityRepository codeSnippetEntityRepository;

    @Override
    public CodeSnippetDto createNew(CodeSnippetDto codeSnippetDto) {
        if (codeSnippetDto.getCodeText().isEmpty()) {
            throw new CodeSnippetEmptyException(ExceptionMessages.FILE_IS_EMPTY);
        }

        CodeSnippetEntity codeSnippetEntity = CodeSnippetMapper.INSTANCE.toEntity(codeSnippetDto);
        return CodeSnippetMapper.INSTANCE.toDto(codeSnippetEntityRepository.save(codeSnippetEntity));
    }

    @Override
    public CodeSnippetDto findById(Long id) {
        CodeSnippetEntity codeSnippetEntity = codeSnippetEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return CodeSnippetMapper.INSTANCE.toDto(codeSnippetEntity);
    }

    @Override
    public List<CodeSnippetDto> findByUserId(Long id) {
        List<CodeSnippetEntity> codeSnippetEntities = codeSnippetEntityRepository.findByUserEntity_id(id);
        return CodeSnippetMapper.INSTANCE.toDtoList(codeSnippetEntities);
    }

    @Override
    public List<CodeSnippetPreviewDto> listAllUserPreviews(Long userId) {
        return codeSnippetEntityRepository.listAllUserPreviews(userId);
    }

    @Override
    public List<CodeSnippetDto> findByLanguage(ProgrammingLanguage programmingLanguage) {
        return CodeSnippetMapper.INSTANCE.toDtoList(codeSnippetEntityRepository.findByLanguageEntity_Name(programmingLanguage.name()));
    }

    @Override
    public CodeSnippetPreviewDto findPreviewById(Long snippetId) {
        return codeSnippetEntityRepository.findPreviewById(snippetId);
    }

    @Override
    public CodeSnippetDto delete(Long id) {
        CodeSnippetEntity codeSnippetEntityToDelete = codeSnippetEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        codeSnippetEntityRepository.delete(codeSnippetEntityToDelete);
        return CodeSnippetMapper.INSTANCE.toDto(codeSnippetEntityToDelete);
    }

    @Override
    public boolean exists(Long id) {
        return codeSnippetEntityRepository.existsById(id);
    }
}
