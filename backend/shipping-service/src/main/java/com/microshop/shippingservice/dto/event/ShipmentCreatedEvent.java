package com.microshop.shippingservice.dto.event;

import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ShipmentCreatedEvent {
    private String shipmentId;
    private String orderId;
    private String shipperId;
    private String status; // ASSIGNED
    private Instant occurredAt;
}
