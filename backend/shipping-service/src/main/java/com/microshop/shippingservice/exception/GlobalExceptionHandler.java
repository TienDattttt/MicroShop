package com.microshop.shippingservice.exception;

import com.microshop.shippingservice.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleApp(AppException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(ex.getErrorCode().getCode())
                        .message(ex.getMessage())
                        .data(null).build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleEx(Exception ex) {
        return ResponseEntity.internalServerError().body(
                ApiResponse.builder()
                        .code(ErrorCode.INTERNAL_ERROR.getCode())
                        .message(ex.getMessage())
                        .data(null).build()
        );
    }
}
