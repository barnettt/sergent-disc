package com.sargentdisc.file.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class SargenDiscExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleInternalServer(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Unknown server error: " + ex.getMessage();
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value
            = {UnableToAccessResourceException.class})
    protected ResponseEntity<Object> handleNotProcessable(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Not Found : " + ex.getMessage();
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
