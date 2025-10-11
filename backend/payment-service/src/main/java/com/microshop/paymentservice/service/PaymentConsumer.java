package com.microshop.paymentservice.service;

import com.microshop.paymentservice.dto.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentProcessorService processor;

    @KafkaListener(topics = "order.events", groupId = "payment-service")
    public void onOrderCreated(@Payload OrderCreatedEvent evt){
        processor.process(evt);
    }
}
