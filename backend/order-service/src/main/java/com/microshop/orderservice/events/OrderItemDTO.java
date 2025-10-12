package com.microshop.orderservice.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO implements Serializable {
    private String productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
}


