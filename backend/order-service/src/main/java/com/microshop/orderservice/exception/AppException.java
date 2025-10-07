package com.microshop.orderservice.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    public AppException(ErrorCode e) {
        super(e.getMessage());
        this.errorCode = e;
    }
}

