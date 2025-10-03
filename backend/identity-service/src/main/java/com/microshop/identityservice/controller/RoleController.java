package com.microshop.identityservice.controller;


import com.microshop.identityservice.dto.request.RoleRequest;
import com.microshop.identityservice.dto.response.ApiResponse;
import com.microshop.identityservice.dto.response.RoleResponse;
import com.microshop.identityservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@Valid @RequestBody RoleRequest req){
        return ApiResponse.ok(roleService.create(req));
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> list(){
        return ApiResponse.ok(roleService.list());
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> delete(@PathVariable String name){
        roleService.delete(name);
        return ApiResponse.ok();
    }
}
