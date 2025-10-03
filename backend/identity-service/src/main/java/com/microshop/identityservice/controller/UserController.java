package com.microshop.identityservice.controller;

import com.microshop.identityservice.dto.request.UserCreationRequest;
import com.microshop.identityservice.dto.request.UserUpdateRequest;
import com.microshop.identityservice.dto.response.ApiResponse;
import com.microshop.identityservice.dto.response.UserResponse;
import com.microshop.identityservice.entity.User;
import com.microshop.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> create(@Valid @RequestBody UserCreationRequest req){
        return ApiResponse.ok(userService.create(req));
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> list(){
        return ApiResponse.ok(userService.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> get(@PathVariable UUID id){
        return ApiResponse.ok(userService.get(id));
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> myInfo(Authentication authentication){
        // subject là userId trong JWT
        UUID id = UUID.fromString(authentication.getName());
        return ApiResponse.ok(userService.get(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> update(@PathVariable UUID id, @RequestBody UserUpdateRequest req){
        return ApiResponse.ok(userService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id){
        userService.delete(id);
        return ApiResponse.ok();
    }
}

