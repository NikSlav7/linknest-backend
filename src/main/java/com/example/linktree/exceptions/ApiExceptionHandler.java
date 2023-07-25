package com.example.linktree.exceptions;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler({CredentialsInUseException.class, NoSuchEntityException.class,
            CredentialsInUseException.class, NoRightsException.class, MaxExceededException.class})
    public ResponseEntity<Object> handleException(Exception exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(exception.getMessage(), status);
    }
}
