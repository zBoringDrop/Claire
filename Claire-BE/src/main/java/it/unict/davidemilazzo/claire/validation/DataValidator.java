package it.unict.davidemilazzo.claire.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.exception.DataValidatorException;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataValidator<T> {

    private static final Logger log = LogManager.getLogger(DataValidator.class);

    private final ObjectMapper objectMapper;

    private final Validator validator;

    public Set<ConstraintViolation<T>> validate(T obj) {
        return validator.validate(obj);
    }

    public void logViolations(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        for (ConstraintViolation<T> violation : violations) {
            log.error(violation.getMessage());
        }
    }

    public HashMap<String, String> getViolationsMap(Set<ConstraintViolation<T>> violations) {
        HashMap<String, String> pathMessage = new HashMap<>(violations.size());
        for (ConstraintViolation<T> violation : violations) {
            pathMessage.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return pathMessage;
    }

    public void validateAndThrowException(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (violations.isEmpty()) return;

        log.warn("Request has some violations: {}", getViolationsMap(violations).toString());
        throw new DataValidatorException(ExceptionMessages.INVALID_DATA, getViolationsMap(violations));
    }
}
