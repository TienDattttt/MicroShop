package com.microshop.orderservice.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddress {
    private String line;
    private String ward;
    private String district;
    private String city;
    private String country;
    private Double lat;
    private Double lon;

    @Override
    public String toString() {
        // Ghép chuỗi hiển thị dễ đọc, bạn có thể tuỳ chỉnh format
        return String.format("%s, %s, %s, %s, %s", line, ward, district, city, country);
    }
}
