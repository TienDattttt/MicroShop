package com.microshop.paymentservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    PAYMENT_NOT_FOUND(5001, "Payment not found", HttpStatus.NOT_FOUND),
    BAD_REQUEST(5400, "Bad request", HttpStatus.BAD_REQUEST);
    private final int code; private final String message; private final HttpStatus status;
    ErrorCode(int c, String m, HttpStatus s){ this.code=c; this.message=m; this.status=s;}
}

