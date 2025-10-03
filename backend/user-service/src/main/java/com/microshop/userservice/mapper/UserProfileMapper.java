package com.microshop.userservice.mapper;

import com.microshop.userservice.dto.response.UserProfileResponse;
import com.microshop.userservice.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileResponse toResponse(UserProfile p);
}

