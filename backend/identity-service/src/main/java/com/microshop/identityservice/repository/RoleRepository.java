package com.microshop.identityservice.repository;

import com.microshop.identityservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> { }
