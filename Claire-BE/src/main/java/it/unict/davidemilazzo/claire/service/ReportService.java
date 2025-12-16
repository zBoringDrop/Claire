package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.ReportDao;
import it.unict.davidemilazzo.claire.dto.AnalysisDto;
import it.unict.davidemilazzo.claire.dto.CodeSnippetDto;
import it.unict.davidemilazzo.claire.dto.FileDto;
import it.unict.davidemilazzo.claire.dto.ReportDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.NotTheOwnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportDao reportDao;
    private final AnalysisService analysisService;
    private final FileService fileService;
    private final CodeSnippetService codeSnippetService;

    public ReportDto getUserAnalysisReport(Long userId, Long analysisId) {
        AnalysisDto analysisDto = analysisService.findById(analysisId);

        if (analysisDto.getFileId() != null) {
            FileDto fileDto = fileService.findById(analysisDto.getFileId());
            if (!Objects.equals(fileDto.getUserId(), userId)) {
                throw new NotTheOwnerException(ExceptionMessages.NOT_THE_OWNER);
            }
        }
        else if (analysisDto.getCodeSnippetId() != null) {
            CodeSnippetDto codeSnippetDto = codeSnippetService.findById(analysisDto.getCodeSnippetId());
            if (!Objects.equals(codeSnippetDto.getUserId(), userId)) {
                throw new NotTheOwnerException(ExceptionMessages.NOT_THE_OWNER);
            }
        }

        return reportDao.findByAnalysisEntity_id(analysisId);
    }
}
