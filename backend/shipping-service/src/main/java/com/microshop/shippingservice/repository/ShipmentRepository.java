package com.microshop.shippingservice.repository;

import com.microshop.shippingservice.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, String> {
    Optional<ShipmentEntity> findByOrderId(String orderId);
    List<ShipmentEntity> findByShipperIdAndStatus(String shipperId, String status);
    List<ShipmentEntity> findByShipperIdAndCreatedAtBetween(String shipperId, Instant start, Instant end);
}
