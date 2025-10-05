package com.microshop.productservice.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {
    private String id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String brand;
    private String categoryId;
    private List<String> images;
    private Double rating;
    private Integer reviewCount;
    private Boolean enabled;
}
