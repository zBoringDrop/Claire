package it.unict.davidemilazzo.claire.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);
    private static ObjectMapper objectMapper;

    @ExceptionHandler({EmailAlreadyRegisteredException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        log.warn("EmailAlreadyRegisteredException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle(ExceptionMessages.EMAIL_ALREADY_REGISTERED);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({UsernameAlreadyRegisteredException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleUsernameAlreadyRegisteredException(UsernameAlreadyRegisteredException ex) {
        log.warn("UsernameAlreadyRegisteredException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle(ExceptionMessages.USERNAME_ALREADY_REGISTERED);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({EmailNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleEmailNotFoundException(EmailNotFoundException ex) {
        log.warn("EmailNotFoundException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle(ExceptionMessages.EMAIL_NOT_FOUND);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({AuthenticationFailedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleAuthenticationFailedException(AuthenticationFailedException ex) {
        log.warn("AuthenticationFailedException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problem.setTitle(ExceptionMessages.AUTHENTICATION_ERROR);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({FileReadException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleInvalidFileException(FileReadException ex) {
        log.warn("InvalidFileException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle(ExceptionMessages.ERROR_ON_FILE_READ);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({FileEmptyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleFileEmptyException(FileEmptyException ex) {
        log.warn("FileEmptyException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.FILE_IS_EMPTY);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({CodeSnippetEmptyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleCodeSnippetEmptyException(CodeSnippetEmptyException ex) {
        log.warn("CodeSnippetEmptyException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.CODE_SNIPPET_IS_EMPTY);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({IdNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleIdNotFound(IdNotFoundException ex) {
        log.warn("IdNotFound: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle(ExceptionMessages.ID_NOT_FOUND);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({NotTheOwnerException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleNotTheOwnerException(NotTheOwnerException ex) {
        log.warn("NotTheOwnerException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problem.setTitle(ExceptionMessages.NOT_THE_OWNER);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({MissingAnalysisReferenceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleMissingAnalysisReferenceException(MissingAnalysisReferenceException ex) {
        log.warn("MissingAnalysisReferenceException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.MISSING_ANALYSIS_SOURCE_REFERENCE);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({ModelSyncException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ProblemDetail handleModelSyncExceptionException(ModelSyncException ex) {
        log.warn("ModelSyncException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);
        problem.setTitle(ExceptionMessages.MODEL_SYNC_FAILED);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({ModelNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex) {
        log.warn("ModelNotFoundException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.MODEL_NOT_FOUND);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({InvalidModelParameterSizeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleInvalidModelParameterSizeException(InvalidModelParameterSizeException ex) {
        log.warn("InvalidModelParameterSizeException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.INVALID_MODEL_PARAMETER_SIZE);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({AIProviderNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleAIProviderNotFoundException(AIProviderNotFoundException ex) {
        log.warn("AIProviderNotFoundException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.AI_PROVIDER_NOT_FOUND);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({JsonParseErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleJsonParseErrorException(JsonParseErrorException ex) {
        log.warn("JsonParseErrorException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.JSON_PARSE_ERROR);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleCategoryNotFoundException(CategoryNotFoundException ex) {
        log.warn("CategoryNotFoundException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.CATEGORY_NOT_FOUND);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({JsonSchemaNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleJsonSchemaNotFoundException(JsonSchemaNotFoundException ex) {
        log.warn("JsonSchemaNotFoundException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.JSON_SCHEMA_NOT_FOUND);
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler({DataValidatorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleDataValidatorException(DataValidatorException ex) {
        log.warn("DataValidatorException: {}", ex.getMessage());
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ExceptionMessages.INVALID_DATA);
        problem.setDetail(ex.getMessage());
        problem.setProperty("violations", ex.getProperties());
        return problem;
    }
}
