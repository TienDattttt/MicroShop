package com.microshop.userservice.controller;


import com.microshop.userservice.dto.request.AddressRequest;
import com.microshop.userservice.dto.response.AddressResponse;
import com.microshop.userservice.dto.response.ApiResponse;
import com.microshop.userservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping
    public ApiResponse<List<AddressResponse>> list(Authentication auth){
        UUID identityId = UUID.fromString(auth.getName());
        return ApiResponse.ok(service.list(identityId));
    }

    @PostMapping
    public ApiResponse<AddressResponse> add(Authentication auth, @RequestBody AddressRequest req){
        UUID identityId = UUID.fromString(auth.getName());
        return ApiResponse.ok(service.add(identityId, req));
    }

    @DeleteMapping("/{addressId}")
    public ApiResponse<Void> delete(Authentication auth, @PathVariable UUID addressId){
        UUID identityId = UUID.fromString(auth.getName());
        service.delete(identityId, addressId);
        return ApiResponse.ok();
    }
}

