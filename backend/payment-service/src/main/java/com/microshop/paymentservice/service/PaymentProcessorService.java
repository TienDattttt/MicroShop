package com.microshop.paymentservice.service;

import com.microshop.paymentservice.dto.event.OrderPlacedEvent;
import com.microshop.paymentservice.dto.event.PaymentFailedEvent;
import com.microshop.paymentservice.dto.event.PaymentSuccessEvent;
import com.microshop.paymentservice.dto.response.PaymentResponse;
import com.microshop.paymentservice.entity.PaymentEntity;
import com.microshop.paymentservice.repository.PaymentRepository;
import com.microshop.paymentservice.service.client.OrderClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessorService {

    private final PaymentRepository repo;
    private final PaymentPublisher publisher;
    private final OrderClient orderClient;

    @Transactional
    public PaymentResponse process(OrderPlacedEvent evt) {
        log.info("💳 Processing payment for order {}", evt.getOrderId());

        UUID orderId = UUID.fromString(evt.getOrderId());
        UUID userId = UUID.fromString(evt.getUserId());
        BigDecimal amount = evt.getTotalAmount() == null
                ? BigDecimal.ZERO
                : evt.getTotalAmount();

        // 1️⃣ Tạo record payment (PROCESSING)
        PaymentEntity p = PaymentEntity.builder()
                .orderId(orderId)
                .userId(userId)
                .amount(amount)
                .method(evt.getPaymentMethod())
                .status("PROCESSING")
                .transactionRef("TXN-" + new Random().nextInt(999999))
                .build();

        p = repo.save(p);

        // 2️⃣ Giả lập xử lý thành công
        boolean ok = true; // có thể random hoặc mock sau này

        if (ok) {
            p.setStatus("SUCCESS");
            repo.save(p);

            // 3️⃣ Gọi order-service cập nhật trạng thái
            updateOrderStatusWithRetry(p.getOrderId(), "PAID");

            // 4️⃣ Publish event thành công
            publisher.publishSuccess(PaymentSuccessEvent.builder()
                    .orderId(p.getOrderId())
                    .userId(p.getUserId())
                    .paymentId(p.getId())
                    .transactionRef(p.getTransactionRef())
                    .amount(p.getAmount())
                    .method(p.getMethod())
                    .build());

            log.info("Payment success for order {}", evt.getOrderId());

        } else {
            p.setStatus("FAILED");
            repo.save(p);

            publisher.publishFailed(PaymentFailedEvent.builder()
                    .orderId(p.getOrderId())
                    .userId(p.getUserId())
                    .reason("Insufficient funds")
                    .build());

            log.warn("Payment failed for order {}", evt.getOrderId());
        }

        return PaymentResponse.builder()
                .id(p.getId())
                .orderId(p.getOrderId())
                .userId(p.getUserId())
                .amount(p.getAmount())
                .method(p.getMethod())
                .status(p.getStatus())
                .transactionRef(p.getTransactionRef())
                .createdAt(p.getCreatedAt())
                .build();
    }

    @Retry(name = "order-status-update", fallbackMethod = "fallbackUpdateStatus")
    public void updateOrderStatusWithRetry(UUID orderId, String status) {
        orderClient.updateStatus(orderId, status);
    }

    public void fallbackUpdateStatus(UUID orderId, String status, Throwable t) {
        log.error("Failed to update order {} status to {}: {}", orderId, status, t.getMessage());
    }
}
