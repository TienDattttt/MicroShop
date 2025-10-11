package com.microshop.paymentservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequest {
    @NotNull private UUID orderId;
    @NotNull private UUID userId;
    @NotNull @Positive private Double amount;
    @NotBlank private String method; // COD/WALLET/CARD
}
