package com.microshop.productservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product extends BaseAudit {
    @Id
    private String id;

    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String brand;
    private String categoryId;
    private List<String> images;
    private Double rating; // trung bình sao
    private Integer reviewCount;
    private Boolean enabled;
}

