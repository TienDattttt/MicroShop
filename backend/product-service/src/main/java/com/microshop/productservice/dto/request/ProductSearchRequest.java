package com.microshop.productservice.dto.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductSearchRequest {
    private String q;          // tên, mô tả
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    private Double minRating;
    private String categoryId;
    @Builder.Default private Integer page = 0;
    @Builder.Default private Integer size = 12;
    @Builder.Default private String sort = "price,asc"; // ví dụ: price,asc | rating,desc
}

