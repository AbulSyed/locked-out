package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    boolean existsByName(String name);
    Set<AuthorityEntity> findByIdIn(List<Long> authorityIds);
}
