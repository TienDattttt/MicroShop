package com.microshop.identityservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoleRequest {
    @NotBlank private String name;
    private String description;
    private Set<String> permissions; // danh sách permission name
}

