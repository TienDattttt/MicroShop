package com.microshop.userservice.service;

import com.microshop.userservice.dto.request.AddressRequest;
import com.microshop.userservice.dto.response.AddressResponse;
import com.microshop.userservice.entity.Address;
import com.microshop.userservice.entity.UserProfile;
import com.microshop.userservice.exception.AppException;
import com.microshop.userservice.exception.ErrorCode;
import com.microshop.userservice.mapper.AddressMapper;
import com.microshop.userservice.repository.AddressRepository;
import com.microshop.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addrRepo;
    private final UserProfileRepository profileRepo;
    private final AddressMapper mapper;

    public List<AddressResponse> list(UUID identityUserId){
        UserProfile p = profileRepo.findByIdentityUserId(identityUserId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        return addrRepo.findByUserProfileId(p.getId()).stream().map(mapper::toResponse).toList();
    }

    @CacheEvict(value="profile", key="#identityUserId")
    public AddressResponse add(UUID identityUserId, AddressRequest req){
        UserProfile p = profileRepo.findByIdentityUserId(identityUserId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        if (Boolean.TRUE.equals(req.getIsDefault())) {
            // bỏ default cũ
            addrRepo.findByUserProfileId(p.getId()).forEach(a -> {
                if (Boolean.TRUE.equals(a.getIsDefault())) { a.setIsDefault(false); addrRepo.save(a); }
            });
        }
        Address a = Address.builder()
                .userProfile(p)
                .line1(req.getLine1()).line2(req.getLine2())
                .city(req.getCity()).district(req.getDistrict()).ward(req.getWard()).country(req.getCountry())
                .isDefault(req.getIsDefault()).note(req.getNote())
                .build();
        return mapper.toResponse(addrRepo.save(a));
    }

    @CacheEvict(value="profile", key="#identityUserId")
    public void delete(UUID identityUserId, UUID addressId){
        UserProfile p = profileRepo.findByIdentityUserId(identityUserId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND));
        Address a = addrRepo.findById(addressId).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!a.getUserProfile().getId().equals(p.getId())) throw new AppException(ErrorCode.UNAUTHORIZED);
        addrRepo.delete(a);
    }
}

