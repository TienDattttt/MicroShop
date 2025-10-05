package com.microshop.productservice.dto.request;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductUpdateRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String brand;
    private String categoryId;
    private List<String> images;
    private Boolean enabled;
}

