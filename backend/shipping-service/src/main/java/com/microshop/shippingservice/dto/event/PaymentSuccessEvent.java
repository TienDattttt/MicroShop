package com.microshop.shippingservice.dto.event;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentSuccessEvent {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private ShippingAddress shippingAddress;
    private BigDecimal codAmount;
    private List<OrderItemDTO> items;

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ShippingAddress {
        private String line;
        private String ward;
        private String district;
        private String city;
        private String country;
        private Double lat;
        private Double lon;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class OrderItemDTO {
        private String productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
    }
}
