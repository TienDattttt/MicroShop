package com.microshop.shippingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class FailRequest {
    @NotBlank
    private String reason;
    private Instant failedTime;
}
