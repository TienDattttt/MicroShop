package com.microshop.paymentservice.repository;

import com.microshop.paymentservice.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findByOrderId(UUID orderId);
}

