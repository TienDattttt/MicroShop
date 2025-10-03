package com.microshop.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions") // Ánh xạ tới bảng "permissions"
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @Column(length = 100) // Khóa chính (Primary Key) và độ dài tên permission
    private String name;

    @Column(length = 255) // Độ dài mô tả
    private String description;

    // Lưu ý: Không cần mapping quan hệ ManyToMany ở đây
    // vì quan hệ (role_permissions) đã được xử lý ở Entity Role.
}
