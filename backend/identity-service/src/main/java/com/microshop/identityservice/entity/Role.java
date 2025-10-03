package com.microshop.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles") // Thêm annotation @Table để ánh xạ đến bảng "roles"
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(length = 60) // Đảm bảo độ dài cột khớp với cấu hình Liquibase/DB
    private String name;

    @Column(length = 255) // Độ dài mô tả
    private String description;

    // Quan hệ Many-to-Many với Permission
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_name"),
            inverseJoinColumns = @JoinColumn(name = "permission_name")
    )
    private Set<Permission> permissions = new HashSet<>();
}
