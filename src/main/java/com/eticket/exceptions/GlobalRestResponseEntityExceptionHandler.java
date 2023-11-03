package com.eticket.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalRestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {io.jsonwebtoken.ExpiredJwtException.class})
    public ResponseEntity<?> expiredJwtException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<?> NoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> generalException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
    }
    @ExceptionHandler(value = {JwtValidationException.class, InvalidBearerTokenException.class})
    public ResponseEntity<?> jwtValidationException(InvalidBearerTokenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {InvalidInputException.class})
    public ResponseEntity<?> invalidInputException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {ApplicationDateException.class})
    public ResponseEntity<?> applicationDateException(ApplicationDateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {UniqueEmailValidationException.class})
    public ResponseEntity<?> invalidStatusException(UniqueEmailValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(value = {SQLGrammarException.class})
    public ResponseEntity<?> sQLGrammarException(SQLGrammarException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

}
