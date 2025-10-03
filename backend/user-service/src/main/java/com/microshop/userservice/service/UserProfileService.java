package com.microshop.userservice.service;


import com.microshop.userservice.dto.request.UserProfileRequest;
import com.microshop.userservice.dto.response.UserProfileResponse;
import com.microshop.userservice.entity.BusinessRole;
import com.microshop.userservice.entity.UserProfile;
import com.microshop.userservice.exception.AppException;
import com.microshop.userservice.exception.ErrorCode;
import com.microshop.userservice.mapper.UserProfileMapper;
import com.microshop.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repo;
    private final UserProfileMapper mapper;

    @Cacheable(value="profile", key="#identityUserId")
    public UserProfileResponse getByIdentity(UUID identityUserId){
        var p = repo.findByIdentityUserId(identityUserId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return mapper.toResponse(p);
    }

    @CacheEvict(value="profile", key="#identityUserId")
    public UserProfileResponse upsertMe(UUID identityUserId, UserProfileRequest req){
        var p = repo.findByIdentityUserId(identityUserId)
                .orElse(UserProfile.builder().identityUserId(identityUserId).build());
        p.setEmail(req.getEmail());
        p.setPhone(req.getPhone());
        p.setFullName(req.getFullName());
        p.setAvatarUrl(req.getAvatarUrl());
        p.setBusinessRole(req.getBusinessRole()==null ? BusinessRole.CUSTOMER : req.getBusinessRole());
        return mapper.toResponse(repo.save(p));
    }
}

