package com.microshop.identityservice.dto.request;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String dob; // yyyy-MM-dd
    private Boolean enabled;
}
