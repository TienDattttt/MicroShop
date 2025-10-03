package com.microshop.identityservice.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserCreationRequest {
    @NotBlank private String username; // có thể là email/phone tuỳ policy
    @NotBlank private String password;
    private String firstName;
    private String lastName;
    private String dob; // yyyy-MM-dd (đơn giản hoá)
}

