package com.binhcodev.product_service.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final HttpStatus status;

    public CommonException(HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }
}
