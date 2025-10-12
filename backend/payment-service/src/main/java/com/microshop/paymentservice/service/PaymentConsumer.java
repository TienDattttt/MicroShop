package com.microshop.paymentservice.service;

import com.microshop.paymentservice.dto.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentProcessorService processor;

    @KafkaListener(topics = "order.events", groupId = "payment-service")
    public void onOrderPlaced(@Payload OrderPlacedEvent evt) {
        log.info("📦 Received OrderPlacedEvent: {}", evt.getOrderId());
        try {
            processor.process(evt);
            log.info("Payment processed successfully for order {}", evt.getOrderId());
        } catch (Exception e) {
            log.error("Error processing order {}: {}", evt.getOrderId(), e.getMessage(), e);
        }
    }

}
