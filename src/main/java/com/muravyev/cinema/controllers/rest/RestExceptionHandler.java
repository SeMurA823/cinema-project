package com.muravyev.cinema.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFound(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(generateResponse(e));
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<?> handleUnprocessableEntity(Exception e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(generateResponse(e));
    }

    private Map<Object, Object> generateResponse(Exception e) {
        return Map.of("timestamp", new Date(),
                "message", Objects.isNull(e.getMessage())?e.getClass().getSimpleName():e.getMessage());
    }
}
