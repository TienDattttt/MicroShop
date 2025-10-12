package com.microshop.shippingservice.dto.request;

import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class PickupRequest {
    private Instant pickupTime;
}
