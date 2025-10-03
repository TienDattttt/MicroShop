package com.microshop.userservice.controller;

import com.microshop.userservice.dto.request.UserProfileRequest;
import com.microshop.userservice.dto.response.ApiResponse;
import com.microshop.userservice.dto.response.UserProfileResponse;
import com.microshop.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> me(Authentication auth){
        UUID identityId = UUID.fromString(auth.getName());
        return ApiResponse.ok(service.getByIdentity(identityId));
    }

    @PutMapping("/me")
    public ApiResponse<UserProfileResponse> updateMe(Authentication auth, @RequestBody UserProfileRequest req){
        UUID identityId = UUID.fromString(auth.getName());
        return ApiResponse.ok(service.upsertMe(identityId, req));
    }
}

