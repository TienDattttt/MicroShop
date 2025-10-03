package com.microshop.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Table(name="user_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfile extends BaseAudit {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name="identity_user_id", nullable=false, unique=true, columnDefinition = "uuid")
    private UUID identityUserId; // subject từ JWT

    private String email;
    private String phone;
    @Column(name="full_name") private String fullName;
    @Column(name="avatar_url") private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(name="business_role")
    private BusinessRole businessRole; // CUSTOMER/SHOP/SHIPPER
}

