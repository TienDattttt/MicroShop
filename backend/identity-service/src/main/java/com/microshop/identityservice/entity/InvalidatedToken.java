package com.microshop.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "invalidated_tokens") // Ánh xạ tới bảng "invalidated_tokens"
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvalidatedToken {

    @Id
    @Column(length = 64) // Khóa chính (Primary Key), thường là JTI hoặc ID của token
    private String id;

    // Thời điểm token hết hạn, sử dụng Instant để lưu trữ thời gian UTC
    private Instant expiryTime;
}