package com.microshop.productservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductCreateRequest {
    @NotBlank private String sku;
    @NotBlank private String name;
    private String description;
    @NotNull @DecimalMin("0.0") private BigDecimal price;
    @NotNull @Min(0) private Integer stock;
    private String brand;
    private String categoryId;
    private List<String> images;
    @Builder.Default private Boolean enabled = true;
}

