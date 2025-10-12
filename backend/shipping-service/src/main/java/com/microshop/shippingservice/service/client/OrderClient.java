package com.microshop.shippingservice.service.client;

import com.microshop.shippingservice.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order-service", url = "${client.order.base-url:}", configuration = FeignConfig.class)
public interface OrderClient {
    // Placeholder for future calls to order-service
}
