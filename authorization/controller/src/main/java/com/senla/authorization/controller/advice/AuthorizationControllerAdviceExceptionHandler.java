package com.senla.authorization.controller.advice;

import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.security.SecurityCommonException;
import com.senla.common.exception.security.jwt.JwtTokenException;
import com.senla.common.exception.security.jwt.JwtTokenNotFoundException;
import com.senla.common.exception.security.jwt.JwtTokenValidationException;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class AuthorizationControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JwtTokenValidationException.class, JwtTokenNotFoundException.class})
    protected ResponseEntity<Object> tokenValidationException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();

        responseBody.put("message", String.format("Can't validate a token! %s", e.getMessage()));
        responseBody.put("time", System.currentTimeMillis());
        log.error("Can't validate a token! {}", e.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = JwtTokenException.class)
    protected ResponseEntity<Object> tokenException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();

        responseBody.put("message", String.format("Token exception! %s", e.getMessage()));
        responseBody.put("time", System.currentTimeMillis());
        log.error("Token exception! {}", e.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = SecurityCommonException.class)
    protected ResponseEntity<Object> webSecurityException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();

        responseBody.put("message", String.format("Web security exception! %s", e.getMessage()));
        responseBody.put("time", System.currentTimeMillis());
        log.error("Web security exception! {}", e.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            jakarta.persistence.EntityNotFoundException.class,
            EntityExistsException.class})
    protected ResponseEntity<Object> notFoundException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();

        responseBody.put("message", String.format("Entity not found exception! %s", e.getMessage()));
        responseBody.put("time", System.currentTimeMillis());
        log.error("Entity not found exception! {}", e.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    protected ResponseEntity<Object> unknownException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();

        responseBody.put("message", String.format("Internal server error! %s", e.getMessage()));
        responseBody.put("time", System.currentTimeMillis());
        log.error("Internal server error! {}", e.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
