package com.microshop.identityservice.dto.response;

import lombok.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoleResponse {
    private String name;
    private String description;
    private Set<String> permissions;
}
