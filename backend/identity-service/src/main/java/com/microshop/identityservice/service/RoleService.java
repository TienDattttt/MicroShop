package com.microshop.identityservice.service;


import com.microshop.identityservice.dto.request.RoleRequest;
import com.microshop.identityservice.dto.response.RoleResponse;
import com.microshop.identityservice.entity.Permission;
import com.microshop.identityservice.entity.Role;
import com.microshop.identityservice.exception.AppException;
import com.microshop.identityservice.exception.ErrorCode;
import com.microshop.identityservice.mapper.RoleMapper;
import com.microshop.identityservice.repository.PermissionRepository;
import com.microshop.identityservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;
    private final RoleMapper roleMapper;

    public RoleResponse create(RoleRequest req){
        roleRepo.findById(req.getName()).ifPresent(r -> { throw new AppException(ErrorCode.ROLE_EXISTED); });
        Set<Permission> perms = req.getPermissions()==null ? Set.of() :
                req.getPermissions().stream()
                        .map(name -> permRepo.findById(name).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)))
                        .collect(Collectors.toSet());
        Role role = Role.builder().name(req.getName()).description(req.getDescription()).permissions(perms).build();
        return roleMapper.toResponse(roleRepo.save(role));
    }

    public List<RoleResponse> list(){
        return roleRepo.findAll().stream().map(roleMapper::toResponse).toList();
    }

    public void delete(String name){
        if (!roleRepo.existsById(name)) throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        roleRepo.deleteById(name);
    }
}
