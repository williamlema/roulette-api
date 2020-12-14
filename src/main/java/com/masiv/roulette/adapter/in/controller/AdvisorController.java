package com.masiv.roulette.adapter.in.controller;

import com.masiv.roulette.kernel.exception.BadRequestException;
import com.masiv.roulette.kernel.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class AdvisorController extends ResponseEntityExceptionHandler {

    private final String TIMESTAMP_KEY = "timestamp";
    private final String MESSAGE_KEY = "message";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception ex) {

        return getObjectResponseEntity(ex);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex) {

        return getObjectResponseEntity(ex);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleThrowable(
            Throwable ex) {

        return getObjectResponseEntity(ex);
    }

    private ResponseEntity<Object> getObjectResponseEntity(Throwable ex) {
        log.error("Generic Error", ex);

        return new ResponseEntity<>(getBodyFromThrowable(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> getBodyFromThrowable (Throwable ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(MESSAGE_KEY, ex.getMessage());

        return body;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(NotFoundException ex) {

        return new ResponseEntity<>(getBodyFromThrowable(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handlerBadRequestException(BadRequestException ex) {

        return new ResponseEntity<>(getBodyFromThrowable(ex), HttpStatus.BAD_REQUEST);
    }
}
