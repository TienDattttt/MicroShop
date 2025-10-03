package com.microshop.userservice.dto.response;

import com.microshop.userservice.entity.BusinessRole;
import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfileResponse {
    private UUID id; private UUID identityUserId;
    private String email; private String phone; private String fullName; private String avatarUrl;
    private BusinessRole businessRole;
}

