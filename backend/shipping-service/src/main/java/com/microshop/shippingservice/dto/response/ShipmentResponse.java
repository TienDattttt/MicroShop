package com.microshop.shippingservice.dto.response;

import lombok.*;
import java.time.Instant;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ShipmentResponse {
    private String shipmentId;
    private String orderId;
    private String shipperId;
    private String status;
    private String buyerName;
    private String buyerPhone;
    private String addressLine;
    private String district;
    private String city;
    private Instant createdAt;
    private Instant pickupTime;
    private Instant deliveredTime;
    private String failureReason;
    private List<String> proofPhotos;
}
