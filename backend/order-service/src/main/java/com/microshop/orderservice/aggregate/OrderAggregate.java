package com.microshop.orderservice.aggregate;

import com.microshop.orderservice.dto.request.OrderCreateRequest;
import com.microshop.orderservice.entity.OrderItem;
import com.microshop.orderservice.enums.OrderStatus;
import com.microshop.orderservice.events.OrderPlacedEvent;
import com.microshop.orderservice.commands.PlaceOrderCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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

    // Khởi tạo trống để Axon tái tạo state khi replay event
    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(PlaceOrderCommand cmd) {
        // Áp dụng sự kiện khi command được xử lý
        AggregateLifecycle.apply(new OrderPlacedEvent(
                cmd.getOrderId(),
                cmd.getUserId(),
                cmd.getShippingAddress(),
                cmd.getPaymentMethod(),
                cmd.getItems()
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
