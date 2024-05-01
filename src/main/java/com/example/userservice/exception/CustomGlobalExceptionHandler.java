package com.example.userservice.exception;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timespan", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityAlreadyPresentException(
            EntityNotFoundException e) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timespan", LocalDateTime.now());
        responseBody.put("status", HttpStatus.NOT_FOUND);
        responseBody.put("exception", e.getMessage());
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyPresentException.class)
    public ResponseEntity<Object> handleEntityAlreadyPresentException(
            EntityAlreadyPresentException e) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timespan", LocalDateTime.now());
        responseBody.put("status", HttpStatus.CONFLICT);
        responseBody.put("exception", e.getMessage());
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidBirthDateException.class, InvalidBirthDateException.class})
    public ResponseEntity<Object> handleInvalidBirthException(
            EntityNotFoundException e) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timespan", LocalDateTime.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST);
        responseBody.put("exception", e.getMessage());
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllOtherExceptions(Exception e) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timespan", LocalDateTime.now());
        responseBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        responseBody.put("exception", e.getMessage());
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError) {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            return field + ": " + message;
        }
        return error.getDefaultMessage();
    }
}
