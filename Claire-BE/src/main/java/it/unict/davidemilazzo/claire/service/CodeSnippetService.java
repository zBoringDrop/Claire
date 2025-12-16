package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.CodeSnippetDao;
import it.unict.davidemilazzo.claire.dto.CodeSnippetDto;
import it.unict.davidemilazzo.claire.dto.CodeSnippetPreviewDto;
import it.unict.davidemilazzo.claire.exception.*;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import it.unict.davidemilazzo.claire.util.ProgrammingLanguageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeSnippetService {

    private static final Logger log = LogManager.getLogger(CodeSnippetService.class);
    private final CodeSnippetDao codeSnippetDao;

    private final ProgrammingLanguageService programmingLanguageService;

    @Transactional
    public CodeSnippetDto uploadNew(CodeSnippetDto codeSnippetDto, Long userId) {
        if (codeSnippetDto.getCodeText().isEmpty()) {
            throw new CodeSnippetEmptyException(ExceptionMessages.CODE_SNIPPET_IS_EMPTY);
        }
        log.info("User {} sent a code snippet: {}", userId, codeSnippetDto);
        codeSnippetDto.setUserId(userId);
        codeSnippetDto.setCreationDatetime(LocalDateTime.now());

        String ext = getSnippetExtFromTitle(codeSnippetDto.getTitle());
        if (!ext.isBlank()) {
            codeSnippetDto.setProgrammingLanguageId(getSnippetExtId(ext));
        }

        codeSnippetDto.setDeleted(false);
        return codeSnippetDao.createNew(codeSnippetDto);
    }

    @Transactional
    public CodeSnippetDto delete(Long id) {
        return codeSnippetDao.delete(id);
    }

    public List<CodeSnippetDto> listUserCodeSnippet(Long userId) {
        return codeSnippetDao.findByUserId(userId);
    }

    public CodeSnippetDto findById(Long id) {
        return codeSnippetDao.findById(id);
    }

    public CodeSnippetPreviewDto findPreviewById(Long snippetId) {
        return codeSnippetDao.findPreviewById(snippetId);
    }

    public List<CodeSnippetPreviewDto> listAllUserPreviews(Long userId) {
        return codeSnippetDao.listAllUserPreviews(userId);
    }

    public void isOwner(Long codeSnippetIdToCheck, Long userId) {
        CodeSnippetDto codeSnippetDto = codeSnippetDao.findById(codeSnippetIdToCheck);
        if (!Objects.equals(userId, codeSnippetDto.getUserId())) {
            throw new NotTheOwnerException(ExceptionMessages.NOT_THE_OWNER);
        }
    }

    public boolean exists(Long codeSnippetId) {
        return codeSnippetDao.exists(codeSnippetId);
    }

    private String getSnippetExtFromTitle(String title) {
        if (title == null || !title.contains(".")) {
            return "";
        }

        return title.substring(title.lastIndexOf('.') + 1).toLowerCase();
    }

    private Long getSnippetExtId(String rawExt) {
        Optional<ProgrammingLanguage> ext = ProgrammingLanguageUtils.fromExtension(rawExt);

        if (ext.isPresent()) {
            return programmingLanguageService.findByName(ext.get().toString()).getId();
        }
        return programmingLanguageService.findByName(ProgrammingLanguage.PSEUDO_CODE.name()).getId();
    }

}
