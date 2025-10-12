package com.microshop.shippingservice.dto.event;

import lombok.*;
import java.time.Instant;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PackageDeliveredEvent {
    private String shipmentId;
    private String orderId;
    private Instant occurredAt;
    private List<String> proofPhotoUrls;
}
