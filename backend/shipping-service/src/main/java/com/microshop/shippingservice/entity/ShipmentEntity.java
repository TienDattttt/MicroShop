package com.microshop.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "shipments")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ShipmentEntity extends BaseAudit {

    @Id
    @Column(name = "shipment_id", length = 40)
    private String shipmentId;

    @Column(name = "order_id", nullable = false, length = 40)
    private String orderId;

    @Column(name = "shipper_id", length = 40)
    private String shipperId;

    @Column(name = "status", length = 30, nullable = false)
    private String status;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_phone", length = 20)
    private String buyerPhone;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "ward")
    private String ward;

    @Column(name = "district")
    private String district;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "pickup_time")
    private Instant pickupTime;

    @Column(name = "delivered_time")
    private Instant deliveredTime;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "proof_photos", columnDefinition = "TEXT")
    private String proofPhotosCsv;
}
