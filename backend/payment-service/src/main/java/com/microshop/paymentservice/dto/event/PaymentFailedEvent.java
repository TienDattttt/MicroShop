package com.microshop.paymentservice.dto.event;

import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentFailedEvent {
    private UUID orderId;
    private UUID userId;
    private String reason;
}
