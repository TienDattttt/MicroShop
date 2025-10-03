package com.microshop.userservice.dto.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressRequest {
    private String line1; private String line2; private String city; private String district; private String ward; private String country;
    private Boolean isDefault; private String note;
}

