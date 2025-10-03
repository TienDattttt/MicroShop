package com.microshop.identityservice.dto.response;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PermissionResponse {
    private String name;
    private String description;
}

