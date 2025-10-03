package com.microshop.identityservice.mapper;

import com.microshop.identityservice.dto.response.PermissionResponse;
import com.microshop.identityservice.entity.Permission;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toResponse(Permission p);
}
