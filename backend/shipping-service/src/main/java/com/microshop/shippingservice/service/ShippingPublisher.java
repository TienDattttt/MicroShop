package com.microshop.shippingservice.service;

import com.microshop.shippingservice.configuration.KafkaConfig;
import com.microshop.shippingservice.dto.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishShipmentCreated(ShipmentCreatedEvent e) {
        kafkaTemplate.send(KafkaConfig.SHIPPING_EVENTS_TOPIC, "ShipmentCreatedEvent", e);
    }
    public void publishPickedUp(PackagePickedUpEvent e) {
        kafkaTemplate.send(KafkaConfig.SHIPPING_EVENTS_TOPIC, "PackagePickedUpEvent", e);
    }
    public void publishDelivered(PackageDeliveredEvent e) {
        kafkaTemplate.send(KafkaConfig.SHIPPING_EVENTS_TOPIC, "PackageDeliveredEvent", e);
    }
    public void publishFailed(DeliveryFailedEvent e) {
        kafkaTemplate.send(KafkaConfig.SHIPPING_EVENTS_TOPIC, "DeliveryFailedEvent", e);
    }
}
