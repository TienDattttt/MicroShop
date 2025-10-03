package com.microshop.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Table(name="addresses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Address extends BaseAudit {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_profile_id", nullable=false)
    private UserProfile userProfile;

    private String line1;
    private String line2;
    private String city;
    private String district;
    private String ward;
    private String country;
    @Column(name="is_default") private Boolean isDefault;
    private String note;
}
