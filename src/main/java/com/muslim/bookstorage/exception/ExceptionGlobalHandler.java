package com.muslim.bookstorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionGlobalHandler {
    @ExceptionHandler({
            UserNotFoundException.class,
            BookNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> notFoundExceptionHandler(RuntimeException exception) {
        return response(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            UserNameAlreadyExistsException.class,
            NotPdfFormatException.class,
            EmptyFileException.class
    })
    public ResponseEntity<Map<String, String>> badRequestExceptionHandler(RuntimeException exception) {
        return response(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> forbiddenExceptionHandler(ForbiddenException exception) {
        return response(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Map<String, String>> response(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(Map.of("Info", message), httpStatus);
    }
}
