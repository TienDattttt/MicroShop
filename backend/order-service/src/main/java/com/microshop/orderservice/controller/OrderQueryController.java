package com.microshop.orderservice.controller;

import com.microshop.orderservice.dto.response.ApiResponse;
import com.microshop.orderservice.dto.response.OrderResponse;
import com.microshop.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderQueryService service;

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getById(@PathVariable UUID id){
        return ApiResponse.ok(service.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getByUser(@PathVariable UUID userId){
        return ApiResponse.ok(service.getByUser(userId));
    }
}

