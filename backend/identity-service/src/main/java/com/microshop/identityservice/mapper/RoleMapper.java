package com.microshop.identityservice.mapper;


import com.microshop.identityservice.dto.response.RoleResponse;
import com.microshop.identityservice.entity.Permission;
import com.microshop.identityservice.entity.Role;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", expression = "java(mapPerms(role.getPermissions()))")
    RoleResponse toResponse(Role role);

    default Set<String> mapPerms(Set<Permission> permissions){
        if (permissions == null) return null;
        return permissions.stream().map(Permission::getName).collect(Collectors.toSet());
    }
}
