package com.binhcodev.product_service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(exception = CommonException.class)
    public ResponseEntity<Object> handleException(CommonException exception){
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}