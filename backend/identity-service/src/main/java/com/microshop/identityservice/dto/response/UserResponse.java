package com.microshop.identityservice.dto.response;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String dob; // yyyy-MM-dd
    private Boolean enabled;
    private Set<String> roles; // role names
}

