package com.aravind.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class MainExceptionClass extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserIdException(UserIdException ex, WebRequest request){
        UserIdExceptionResponse exceptionResponse = new UserIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
