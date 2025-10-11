package com.microshop.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name="payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentEntity extends BaseAudit {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(columnDefinition="uuid")
    private UUID id;

    @Column(name="order_id") private UUID orderId;
    @Column(name="user_id")  private UUID userId;
    private BigDecimal amount;
    private String method;         // COD, WALLET, CARD
    private String status;         // PROCESSING, SUCCESS, FAILED
    @Column(name="transaction_ref") private String transactionRef;
}

