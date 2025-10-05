package com.microshop.productservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(3001, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_DUPLICATED_SKU(3002, "Product SKU already exists", HttpStatus.CONFLICT),
    BAD_REQUEST(3400, "Bad request", HttpStatus.BAD_REQUEST);

    private final int code; private final String message; private final HttpStatus status;
    ErrorCode(int c, String m, HttpStatus s){ this.code=c; this.message=m; this.status=s; }
}

