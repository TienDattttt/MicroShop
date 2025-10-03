package com.microshop.identityservice.service;


import com.microshop.identityservice.dto.request.PermissionRequest;
import com.microshop.identityservice.dto.response.PermissionResponse;
import com.microshop.identityservice.entity.Permission;
import com.microshop.identityservice.exception.AppException;
import com.microshop.identityservice.exception.ErrorCode;
import com.microshop.identityservice.mapper.PermissionMapper;
import com.microshop.identityservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permRepo;
    private final PermissionMapper permMapper;

    public PermissionResponse create(PermissionRequest req){
        permRepo.findById(req.getName()).ifPresent(p -> { throw new AppException(ErrorCode.PERMISSION_EXISTED); });
        Permission p = Permission.builder().name(req.getName()).description(req.getDescription()).build();
        return permMapper.toResponse(permRepo.save(p));
    }

    public List<PermissionResponse> list(){
        return permRepo.findAll().stream().map(permMapper::toResponse).toList();
    }

    public void delete(String name){
        if (!permRepo.existsById(name)) throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        permRepo.deleteById(name);
    }
}
