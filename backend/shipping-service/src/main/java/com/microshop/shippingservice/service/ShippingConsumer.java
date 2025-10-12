package com.microshop.shippingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microshop.shippingservice.configuration.KafkaConfig;
import com.microshop.shippingservice.dto.event.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingConsumer {

    private final ShippingProcessorService processor;
    private final ObjectMapper mapper;

    // ✅ Shipping sẽ nghe topic "payment.events"
    @KafkaListener(topics = KafkaConfig.PAYMENT_EVENTS_TOPIC, groupId = "shipping-service")
    public void onPaymentEvent(ConsumerRecord<String, String> record) {
        try {
            PaymentSuccessEvent evt = mapper.readValue(record.value(), PaymentSuccessEvent.class);
            log.info("📦 Received PaymentSuccessEvent for order {}", evt.getOrderId());
            processor.createShipmentFromPayment(evt);
        } catch (Exception ex) {
            log.error("❌ Failed to process payment event: {}", record.value(), ex);
        }
    }
}
