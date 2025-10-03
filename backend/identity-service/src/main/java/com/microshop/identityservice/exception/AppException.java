package com.microshop.identityservice.exception;


import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppException(ErrorCode code) {
        super(code.getMessage());
        this.errorCode = code;
    }
}

