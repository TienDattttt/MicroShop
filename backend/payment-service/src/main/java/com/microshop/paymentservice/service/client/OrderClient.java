package com.microshop.paymentservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "order-service")
public interface OrderClient {

    @PutMapping("/order/command/update-status/{orderId}")
    void updateStatus(@PathVariable("orderId") UUID orderId,
                      @RequestParam("status") String status);
}


