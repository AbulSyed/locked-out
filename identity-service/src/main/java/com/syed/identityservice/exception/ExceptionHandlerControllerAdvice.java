package com.syed.identityservice.exception;

import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String HANDLING_EXCEPTION_LOG_MESSAGE = "{} handling exception {}";

    // this exception is thrown by the @Valid annotation
    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(((FieldError) error).getField())
                    .append(": ")
                    .append(error.getDefaultMessage());
        });

        return errorDetailsResponseEntity(errorMessage.toString(), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return errorDetailsResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleFieldAlreadyExistsException(FieldAlreadyExistsException ex) {
        return errorDetailsResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return errorDetailsResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, ex);
    }

    public ResponseEntity<Object> errorDetailsResponseEntity(String errorMessage, HttpStatus status, Exception ex) {
        log.info(HANDLING_EXCEPTION_LOG_MESSAGE, applicationName, errorMessage, ex);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(errorMessage);
        errorResponse.setStatus(status);

        return new ResponseEntity<>(errorResponse, status);
    }
}
