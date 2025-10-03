package com.microshop.identityservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PermissionRequest {
    @NotBlank private String name;
    private String description;
}

