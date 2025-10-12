package com.microshop.shippingservice.controller;

import com.microshop.shippingservice.dto.response.ApiResponse;
import com.microshop.shippingservice.dto.response.ShipmentResponse;
import com.microshop.shippingservice.service.ShippingProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentQueryController {

    private final ShippingProcessorService service;

    @GetMapping("/{id}")
    public ApiResponse<ShipmentResponse> get(@PathVariable("id") String id) {
        return ApiResponse.ok(service.getById(id));
    }

    @GetMapping("/today")
    public ApiResponse<List<ShipmentResponse>> today(@RequestParam String shipperId) {
        return ApiResponse.ok(service.getTodayForShipper(shipperId));
    }

    @GetMapping("/to-pickup")
    public ApiResponse<List<ShipmentResponse>> toPickup(@RequestParam String shipperId) {
        return ApiResponse.ok(service.getByStatusForShipper(shipperId, "ASSIGNED"));
    }
}
