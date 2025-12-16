package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.CodeSnippetDto;
import it.unict.davidemilazzo.claire.dto.CodeSnippetPreviewDto;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;

import java.util.List;

public interface CodeSnippetDao {
    CodeSnippetDto createNew(CodeSnippetDto codeSnippetDto);
    CodeSnippetDto findById(Long id);
    List<CodeSnippetDto> findByUserId(Long id);
    List<CodeSnippetPreviewDto> listAllUserPreviews(Long userId);
    List<CodeSnippetDto> findByLanguage(ProgrammingLanguage programmingLanguage);
    CodeSnippetPreviewDto findPreviewById(Long snippetId);
    CodeSnippetDto delete(Long id);
    boolean exists(Long id);
}
