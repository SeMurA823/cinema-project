package com.muravyev.cinema.controllers;

import com.muravyev.cinema.security.exceptions.InvalidTokenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, IndexOutOfBoundsException.class})
    public ResponseEntity<?> handleNotFound(Exception e) {
        log.error(e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(generateResponse(e, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<?> handleUnprocessableEntity(Exception e) {
        log.error(e);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(generateResponse(e, HttpStatus.UNPROCESSABLE_ENTITY));
    }
    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<?> handleInvalidToken(Exception e) {
        log.error(e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(generateResponse(e,
                        "Invalid token",
                        HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(generateResponse(e, HttpStatus.BAD_REQUEST));
    }

    private Map<Object, Object> generateResponse(Exception e, String message, HttpStatus status) {
        return Map.of("status_code", status.value(),
                "timestamp", new Date(),
                "message", message);
    }

    private Map<Object, Object> generateResponse(Exception e, HttpStatus status) {
        return Map.of("status_code", status.value(),
                "timestamp", new Date(),
                "message", Objects.isNull(e.getMessage())?e.getClass().getSimpleName():e.getMessage());
    }
}
