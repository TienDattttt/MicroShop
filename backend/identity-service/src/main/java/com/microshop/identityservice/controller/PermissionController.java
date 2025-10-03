package com.microshop.identityservice.controller;


import com.microshop.identityservice.dto.request.PermissionRequest;
import com.microshop.identityservice.dto.response.ApiResponse;
import com.microshop.identityservice.dto.response.PermissionResponse;
import com.microshop.identityservice.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@Valid @RequestBody PermissionRequest req){
        return ApiResponse.ok(permService.create(req));
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> list(){
        return ApiResponse.ok(permService.list());
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> delete(@PathVariable String name){
        permService.delete(name);
        return ApiResponse.ok();
    }
}

