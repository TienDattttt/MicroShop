package com.microshop.paymentservice.service;

import com.microshop.paymentservice.dto.event.PaymentFailedEvent;
import com.microshop.paymentservice.dto.event.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSuccess(PaymentSuccessEvent evt){
        kafkaTemplate.send("payment.events", evt);
    }

    public void publishFailed(PaymentFailedEvent evt){
        kafkaTemplate.send("payment.events", evt);
    }
}
