package com.microshop.identityservice.dto.response;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class IntrospectResponse {
    private boolean active;
    private String subject;
    private String scope; // space-separated scopes
    private Long exp;     // epoch seconds
}
