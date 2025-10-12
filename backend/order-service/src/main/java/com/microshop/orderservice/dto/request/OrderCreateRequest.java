package com.microshop.orderservice.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderCreateRequest {
    @NotNull private UUID userId;
    @NotNull private ShippingAddress shippingAddress;
    @NotBlank private String paymentMethod;

    @NotEmpty
    private List<ItemRequest> items;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ItemRequest {
        @NotBlank private String productId;
        @NotBlank private String productName;
        @NotNull @Positive private Double price;
        @NotNull @Min(1) private Integer quantity;
    }

    public String getShippingAddressString() {
        return (shippingAddress != null) ? shippingAddress.toString() : null;
    }
}
