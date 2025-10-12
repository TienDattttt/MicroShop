package com.microshop.orderservice.service;

import com.microshop.orderservice.dto.request.OrderCreateRequest;
import com.microshop.orderservice.dto.response.ApiResponse;
import com.microshop.orderservice.dto.response.OrderResponse;
import com.microshop.orderservice.entity.OrderEntity;
import com.microshop.orderservice.entity.OrderItem;
import com.microshop.orderservice.events.OrderItemDTO;
import com.microshop.orderservice.events.OrderPlacedEvent;
import com.microshop.orderservice.exception.AppException;
import com.microshop.orderservice.exception.ErrorCode;
import com.microshop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public ApiResponse<?> updateStatus(UUID orderId, String status) {
        return orderRepo.findById(orderId)
                .map(order -> {
                    order.setStatus(status.toUpperCase());
                    orderRepo.save(order);
                    return ApiResponse.ok("Order status updated to " + status);
                })
                .orElse(ApiResponse.error("Order not found"));
    }
    public OrderResponse placeOrder(OrderCreateRequest req) {
        if (req.getItems() == null || req.getItems().isEmpty())
            throw new AppException(ErrorCode.BAD_REQUEST);

        // 🧮 Tính tổng tiền
        BigDecimal total = req.getItems().stream()
                .map(i -> BigDecimal.valueOf(i.getPrice())
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 🏗️ Tạo OrderEntity
        OrderEntity order = OrderEntity.builder()
                .userId(req.getUserId())
                .status("PENDING")
                .paymentMethod(req.getPaymentMethod())
                .shippingAddress(req.getShippingAddress())
                .totalAmount(total)
                .build();

        // 🧩 Map items
        List<OrderItem> items = req.getItems().stream()
                .map(i -> OrderItem.builder()
                        .order(order)
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .price(BigDecimal.valueOf(i.getPrice()))
                        .quantity(i.getQuantity())
                        .build())
                .toList();

        order.setItems(items);
        OrderEntity saved = orderRepo.save(order);

        List<OrderItemDTO> itemDTOs = saved.getItems().stream()
                .map(i -> new OrderItemDTO(
                        i.getProductId(),
                        i.getProductName(),
                        i.getPrice(),
                        i.getQuantity()
                ))
                .toList();

        kafkaTemplate.send("order.events", new OrderPlacedEvent(
                saved.getId().toString(),
                saved.getUserId().toString(),
                saved.getShippingAddress(),
                saved.getPaymentMethod(),
                saved.getTotalAmount(),
                itemDTOs
        ));

        return toResponse(saved);
    }

    private OrderResponse toResponse(OrderEntity e){
        return OrderResponse.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .status(e.getStatus())
                .paymentMethod(e.getPaymentMethod())
                .shippingAddress(e.getShippingAddress())
                .totalAmount(e.getTotalAmount())
                .createdAt(e.getCreatedAt())
                .items(e.getItems().stream().map(i ->
                                new OrderResponse.ItemResponse(i.getProductId(), i.getProductName(),
                                        i.getPrice().doubleValue(), i.getQuantity()))
                        .toList())
                .build();
    }
}
