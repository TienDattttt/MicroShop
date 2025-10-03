package com.microshop.userservice.dto.response;

import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApiResponse<T> {
    private boolean success; private String message; private T data;
    public static <T> ApiResponse<T> ok(T d){ return ApiResponse.<T>builder().success(true).message("OK").data(d).build();}
    public static <T> ApiResponse<T> ok(){ return ApiResponse.<T>builder().success(true).message("OK").build();}
    public static <T> ApiResponse<T> error(String m){ return ApiResponse.<T>builder().success(false).message(m).build();}
}
