package com.microshop.paymentservice.service;

import com.microshop.paymentservice.dto.event.OrderCreatedEvent;
import com.microshop.paymentservice.dto.event.PaymentFailedEvent;
import com.microshop.paymentservice.dto.event.PaymentSuccessEvent;
import com.microshop.paymentservice.dto.response.PaymentResponse;
import com.microshop.paymentservice.entity.PaymentEntity;
import com.microshop.paymentservice.repository.PaymentRepository;
import com.microshop.paymentservice.service.client.OrderClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentProcessorService {

    private final PaymentRepository repo;
    private final PaymentPublisher publisher;
    private final OrderClient orderClient;

    @Transactional
    public PaymentResponse process(OrderCreatedEvent evt){
        // 1) tạo record payment (PROCESSING)
        PaymentEntity p = PaymentEntity.builder()
                .orderId(evt.getOrderId())
                .userId(evt.getUserId())
                .amount(evt.getTotalAmount() == null ? BigDecimal.ZERO : evt.getTotalAmount())
                .method("COD") // demo
                .status("PROCESSING")
                .transactionRef("TXN-" + new Random().nextInt(999999))
                .build();
        p = repo.save(p);

        // 2) giả lập xử lý thành công (em có thể random fail để test)
        boolean ok = true;

        if (ok) {
            p.setStatus("SUCCESS");
            repo.save(p);

            // 3) gọi order-service cập nhật trạng thái (Retry nếu lỗi)
            updateOrderStatusWithRetry(p.getOrderId(), "PAID");

            // 4) publish event thành công
            publisher.publishSuccess(PaymentSuccessEvent.builder()
                    .orderId(p.getOrderId())
                    .userId(p.getUserId())
                    .paymentId(p.getId())
                    .transactionRef(p.getTransactionRef())
                    .amount(p.getAmount())
                    .method(p.getMethod())
                    .build());

        } else {
            p.setStatus("FAILED");
            repo.save(p);
            publisher.publishFailed(PaymentFailedEvent.builder()
                    .orderId(p.getOrderId()).userId(p.getUserId())
                    .reason("Insufficient funds")
                    .build());
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
    public void updateOrderStatusWithRetry(UUID orderId, String status){
        orderClient.updateStatus(orderId, status);
    }
    public void fallbackUpdateStatus(UUID orderId, String status, Throwable t){
        // ghi log hoặc enqueue retry async
    }
}

