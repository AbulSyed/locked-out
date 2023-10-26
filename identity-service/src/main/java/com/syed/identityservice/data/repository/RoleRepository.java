package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
