package com.microshop.userservice.repository;

import com.microshop.userservice.entity.Address;
import com.microshop.userservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByUserProfileId(UUID userProfileId);
    long countByUserProfileId(UUID userProfileId);
}

