package com.microshop.identityservice.dto.response;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn; // seconds
    private String tokenType; // "Bearer"
}
