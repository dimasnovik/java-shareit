package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoSuchElement(final RuntimeException e) {
        log.debug("404 Not found {}", e.getMessage(), e);
        return Map.of("Wrong ID", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserAlreadyExistException(final DuplicateEmailException e) {
        log.debug("409 Conflict {}", e.getMessage(), e);
        return Map.of("Duplicate email", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleWrongUserAccessException(final WrongUserAccessException e) {
        log.debug("403 Forbidden {}", e.getMessage(), e);
        return Map.of("Wrong user", e.getMessage());
    }

}
