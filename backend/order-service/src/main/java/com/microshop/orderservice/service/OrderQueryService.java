package com.microshop.orderservice.service;

import com.microshop.orderservice.dto.response.OrderResponse;
import com.microshop.orderservice.entity.OrderEntity;
import com.microshop.orderservice.exception.AppException;
import com.microshop.orderservice.exception.ErrorCode;
import com.microshop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepo;

    public List<OrderResponse> getByUser(UUID userId) {
        return orderRepo.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    public OrderResponse getById(UUID id) {
        OrderEntity e = orderRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return toResponse(e);
    }

    private OrderResponse toResponse(OrderEntity e){
        return OrderResponse.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .status(e.getStatus())
                .totalAmount(e.getTotalAmount())
                .paymentMethod(e.getPaymentMethod())
                .shippingAddress(e.getShippingAddress())
                .createdAt(e.getCreatedAt())
                .items(e.getItems().stream().map(i ->
                                new OrderResponse.ItemResponse(i.getProductId(), i.getProductName(),
                                        i.getPrice().doubleValue(), i.getQuantity()))
                        .toList())
                .build();
    }
}

