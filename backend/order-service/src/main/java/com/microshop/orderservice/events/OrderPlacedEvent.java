package com.microshop.orderservice.events;

import com.microshop.orderservice.entity.OrderItem;

import java.util.List;

public class OrderPlacedEvent {
    private String orderId;
    private String userId;
    private String shippingAddress;
    private String paymentMethod;
    private List<OrderItem> items;

    public OrderPlacedEvent(String orderId, String userId, String shippingAddress, String paymentMethod, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.items = items;
    }

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getShippingAddress() { return shippingAddress; }
    public String getPaymentMethod() { return paymentMethod; }
    public List<OrderItem> getItems() { return items; }
}
