package com.microshop.paymentservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent implements Serializable {
    private String orderId;
    private String userId;
    private String shippingAddress;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> items;
}
