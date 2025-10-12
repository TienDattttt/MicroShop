package com.microshop.shippingservice.service;

import com.microshop.shippingservice.dto.event.*;
import com.microshop.shippingservice.dto.response.ShipmentResponse;
import com.microshop.shippingservice.entity.ShipmentEntity;
import com.microshop.shippingservice.exception.AppException;
import com.microshop.shippingservice.exception.ErrorCode;
import com.microshop.shippingservice.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingProcessorService {

    private final ShipmentRepository repo;
    private final ShippingPublisher publisher;
    private final RouteAssignmentService routeService;
    private final SimpMessagingTemplate ws;

    @Transactional
    public ShipmentResponse createShipmentFromPayment(PaymentSuccessEvent e) {
        String shipmentId = "SHP-" + UUID.randomUUID();

        String shipperId = routeService.assignShipperByDistrict(
                e.getShippingAddress() != null ? e.getShippingAddress().getDistrict() : null
        );

        ShipmentEntity en = ShipmentEntity.builder()
                .shipmentId(shipmentId)
                .orderId(e.getOrderId())
                .shipperId(shipperId)
                .status("ASSIGNED")
                .buyerName(e.getBuyerName())
                .buyerPhone(e.getBuyerPhone())
                .addressLine(e.getShippingAddress() != null ? e.getShippingAddress().getLine() : null)
                .ward(e.getShippingAddress() != null ? e.getShippingAddress().getWard() : null)
                .district(e.getShippingAddress() != null ? e.getShippingAddress().getDistrict() : null)
                .city(e.getShippingAddress() != null ? e.getShippingAddress().getCity() : null)
                .country(e.getShippingAddress() != null ? e.getShippingAddress().getCountry() : null)
                .lat(e.getShippingAddress() != null ? e.getShippingAddress().getLat() : null)
                .lon(e.getShippingAddress() != null ? e.getShippingAddress().getLon() : null)
                .build();
        repo.save(en);

        ShipmentCreatedEvent evt = ShipmentCreatedEvent.builder()
                .shipmentId(shipmentId)
                .orderId(e.getOrderId())
                .status("ASSIGNED")
                .shipperId(shipperId)
                .occurredAt(Instant.now())
                .build();
        publisher.publishShipmentCreated(evt);
        ws.convertAndSend("/topic/shipments/" + shipmentId, toResponse(en));

        return toResponse(en);
    }

    @Transactional
    public ShipmentResponse markPickedUp(String shipmentId, Instant time) {
        ShipmentEntity en = repo.findById(shipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Shipment not found"));
        if (!List.of("ASSIGNED","PICKED_UP").contains(en.getStatus())) {
            throw new AppException(ErrorCode.INVALID_STATE, "Invalid state to pickup: " + en.getStatus());
        }
        en.setStatus("PICKED_UP");
        en.setPickupTime(time != null ? time : Instant.now());
        repo.save(en);

        publisher.publishPickedUp(PackagePickedUpEvent.builder()
                .shipmentId(en.getShipmentId())
                .orderId(en.getOrderId())
                .occurredAt(Instant.now()).build());
        ws.convertAndSend("/topic/shipments/" + shipmentId, toResponse(en));
        return toResponse(en);
    }

    @Transactional
    public ShipmentResponse markDelivered(String shipmentId, Instant time, List<String> photoUrls) {
        ShipmentEntity en = repo.findById(shipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Shipment not found"));

        en.setStatus("DELIVERED");
        en.setDeliveredTime(time != null ? time : Instant.now());
        en.setProofPhotosCsv(photoUrls != null ? String.join(";", photoUrls) : null);
        repo.save(en);

        publisher.publishDelivered(PackageDeliveredEvent.builder()
                .shipmentId(en.getShipmentId())
                .orderId(en.getOrderId())
                .occurredAt(Instant.now())
                .proofPhotoUrls(photoUrls)
                .build());
        ws.convertAndSend("/topic/shipments/" + shipmentId, toResponse(en));
        return toResponse(en);
    }

    @Transactional
    public ShipmentResponse markFailed(String shipmentId, Instant time, String reason) {
        ShipmentEntity en = repo.findById(shipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Shipment not found"));

        en.setStatus("FAILED");
        en.setDeliveredTime(time != null ? time : Instant.now());
        en.setFailureReason(reason);
        repo.save(en);

        publisher.publishFailed(DeliveryFailedEvent.builder()
                .shipmentId(en.getShipmentId())
                .orderId(en.getOrderId())
                .occurredAt(Instant.now())
                .reason(reason).build());
        ws.convertAndSend("/topic/shipments/" + shipmentId, toResponse(en));
        return toResponse(en);
    }

    public ShipmentResponse getById(String id) {
        return repo.findById(id).map(this::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Shipment not found"));
    }

    public List<ShipmentResponse> getTodayForShipper(String shipperId) {
        var assigned = repo.findByShipperIdAndStatus(shipperId, "ASSIGNED").stream().map(this::toResponse).toList();
        var picked = repo.findByShipperIdAndStatus(shipperId, "PICKED_UP").stream().map(this::toResponse).toList();
        java.util.ArrayList<ShipmentResponse> out = new java.util.ArrayList<>();
        out.addAll(assigned); out.addAll(picked);
        return out;
    }

    public List<ShipmentResponse> getByStatusForShipper(String shipperId, String status) {
        return repo.findByShipperIdAndStatus(shipperId, status).stream().map(this::toResponse).toList();
    }

    private ShipmentResponse toResponse(ShipmentEntity en) {
        return ShipmentResponse.builder()
                .shipmentId(en.getShipmentId())
                .orderId(en.getOrderId())
                .shipperId(en.getShipperId())
                .status(en.getStatus())
                .buyerName(en.getBuyerName())
                .buyerPhone(en.getBuyerPhone())
                .addressLine(en.getAddressLine())
                .district(en.getDistrict())
                .city(en.getCity())
                .createdAt(en.getCreatedAt())
                .pickupTime(en.getPickupTime())
                .deliveredTime(en.getDeliveredTime())
                .failureReason(en.getFailureReason())
                .proofPhotos(en.getProofPhotosCsv() != null ? Arrays.asList(en.getProofPhotosCsv().split(";")) : null)
                .build();
    }
}
