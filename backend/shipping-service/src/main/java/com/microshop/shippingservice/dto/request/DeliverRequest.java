package com.microshop.shippingservice.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class DeliverRequest {
    private Instant deliveredTime;
    private MultipartFile[] photos;
}
