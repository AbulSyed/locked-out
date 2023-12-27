package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    boolean existsByName(String name);
    Set<RoleEntity> findByIdIn(List<Long> roleIds);
}
