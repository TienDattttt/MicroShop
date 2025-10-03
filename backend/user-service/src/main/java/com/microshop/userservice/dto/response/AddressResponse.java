package com.microshop.userservice.dto.response;

import lombok.*; import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressResponse {
    private UUID id; private String line1; private String line2; private String city; private String district; private String ward; private String country;
    private Boolean isDefault; private String note;
}
