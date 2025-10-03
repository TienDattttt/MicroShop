package com.microshop.identityservice.mapper;


import com.microshop.identityservice.dto.response.UserResponse;
import com.microshop.identityservice.entity.Role;
import com.microshop.identityservice.entity.User;
import org.mapstruct.*;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "dob", expression = "java(user.getDob() == null ? null : user.getDob().toString())")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toResponse(User user);

    default Set<String> mapRoles(Set<Role> roles){
        if (roles == null) return null;
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}

