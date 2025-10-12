package com.microshop.shippingservice.dto.event;

import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PackagePickedUpEvent {
    private String shipmentId;
    private String orderId;
    private Instant occurredAt;
}
