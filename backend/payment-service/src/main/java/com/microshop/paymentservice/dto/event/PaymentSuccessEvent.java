package com.microshop.paymentservice.dto.event;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentSuccessEvent {
    private UUID orderId;
    private UUID userId;
    private UUID paymentId;
    private String transactionRef;
    private BigDecimal amount;
    private String method;
}

