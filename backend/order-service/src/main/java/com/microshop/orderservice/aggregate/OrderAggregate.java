package com.microshop.orderservice.aggregate;

import com.microshop.orderservice.commands.PlaceOrderCommand;
import com.microshop.orderservice.enums.OrderStatus;
import com.microshop.orderservice.events.OrderItemDTO;
import com.microshop.orderservice.events.OrderPlacedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String userId;
    private OrderStatus status;
    private String shippingAddress;
    private String paymentMethod;

    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(PlaceOrderCommand cmd) {
        BigDecimal total = cmd.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<OrderItemDTO> itemDTOs = cmd.getItems().stream()
                .map(i -> new OrderItemDTO(
                        i.getProductId(),
                        i.getProductName(),
                        i.getPrice(),
                        i.getQuantity()
                ))
                .collect(Collectors.toList());

        AggregateLifecycle.apply(new OrderPlacedEvent(
                cmd.getOrderId(),
                cmd.getUserId(),
                cmd.getShippingAddress(),
                cmd.getPaymentMethod(),
                total,
                itemDTOs
        ));
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.shippingAddress = event.getShippingAddress();
        this.paymentMethod = event.getPaymentMethod();
        this.status = OrderStatus.PENDING;
    }
}
