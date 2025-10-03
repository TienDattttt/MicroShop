package com.microshop.identityservice.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_DISABLED(1002, "User disabled", HttpStatus.FORBIDDEN),
    USER_EXISTED(1003, "User already exists", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(1101, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1102, "Role already exists", HttpStatus.CONFLICT),
    PERMISSION_NOT_FOUND(1201, "Permission not found", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(1202, "Permission already exists", HttpStatus.CONFLICT),
    AUTHENTICATION_FAILED(1301, "Authentication failed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1302, "Unauthorized", HttpStatus.UNAUTHORIZED);

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status){
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
