package com.microshop.orderservice.controller;

import com.microshop.orderservice.dto.request.OrderCreateRequest;
import com.microshop.orderservice.dto.response.ApiResponse;
import com.microshop.orderservice.dto.response.OrderResponse;
import com.microshop.orderservice.service.OrderCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/command")
@RequiredArgsConstructor
public class OrderCommandController {

    private final OrderCommandService service;

    @PostMapping("/place")
    public ApiResponse<OrderResponse> placeOrder(@Valid @RequestBody OrderCreateRequest req){
        return ApiResponse.ok(service.placeOrder(req));
    }
}
