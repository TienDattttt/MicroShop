package com.microshop.paymentservice.controller;

import com.microshop.paymentservice.dto.response.ApiResponse;
import com.microshop.paymentservice.dto.response.PaymentResponse;
import com.microshop.paymentservice.entity.PaymentEntity;
import com.microshop.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class PaymentQueryController {

    private final PaymentRepository repo;

    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> get(@PathVariable UUID paymentId){
        PaymentEntity p = repo.findById(paymentId).orElseThrow();
        return ApiResponse.ok(PaymentResponse.builder()
                .id(p.getId()).orderId(p.getOrderId()).userId(p.getUserId())
                .amount(p.getAmount()).method(p.getMethod()).status(p.getStatus())
                .transactionRef(p.getTransactionRef()).createdAt(p.getCreatedAt()).build());
    }
}
