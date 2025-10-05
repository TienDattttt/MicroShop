package com.microshop.productservice.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Document(indexName = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductSearchDocument {
    @Id
    private String id;

    @Field(type = FieldType.Keyword) private String sku;
    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard") private String name;
    @Field(type = FieldType.Text, analyzer = "standard") private String description;
    @Field(type = FieldType.Double) private BigDecimal price;
    @Field(type = FieldType.Integer) private Integer stock;
    @Field(type = FieldType.Keyword) private String brand;
    @Field(type = FieldType.Keyword, name = "category_id") private String categoryId;
    @Field(type = FieldType.Keyword) private List<String> images;
    @Field(type = FieldType.Double) private Double rating;
    @Field(type = FieldType.Integer, name = "review_count") private Integer reviewCount;
    @Field(type = FieldType.Boolean) private Boolean enabled;
}
