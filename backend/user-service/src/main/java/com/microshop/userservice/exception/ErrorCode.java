package com.microshop.userservice.exception;

import lombok.Getter; import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    PROFILE_NOT_FOUND(2001, "Profile not found", HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND(2002, "Address not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(2301, "Unauthorized", HttpStatus.UNAUTHORIZED);
    private final int code; private final String message; private final HttpStatus status;
    ErrorCode(int c,String m,HttpStatus s){this.code=c;this.message=m;this.status=s;}
}
