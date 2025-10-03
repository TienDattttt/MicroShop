package com.microshop.userservice.dto.request;
import com.microshop.userservice.entity.BusinessRole;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfileRequest {
    private String email; private String phone;
    @NotBlank private String fullName;
    private String avatarUrl;
    private BusinessRole businessRole; // nếu null → mặc định CUSTOMER
}

