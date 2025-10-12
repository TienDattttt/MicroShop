package com.microshop.shippingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class AssignShipperRequest {
    @NotBlank
    private String shipperId;
}
