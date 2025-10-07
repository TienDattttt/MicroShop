package com.microshop.orderservice.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ORDER_NOT_FOUND(4001, "Order not found", HttpStatus.NOT_FOUND),
    BAD_REQUEST(4400, "Bad request", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int c, String m, HttpStatus s) {
        this.code = c; this.message = m; this.status = s;
    }
}
