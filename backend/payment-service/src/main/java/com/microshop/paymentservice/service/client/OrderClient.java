package com.microshop.paymentservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "order-service")
public interface OrderClient {

    // ví dụ endpoint tạm (em có thể thêm trong order-service khi cần)
    @PutMapping("/order/command/update-status/{orderId}")
    void updateStatus(@PathVariable("orderId") UUID orderId, @RequestParam("status") String status);
}
