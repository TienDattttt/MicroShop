package com.microshop.identityservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LogoutRequest {
    @NotBlank private String token; // access token cần logout
}
