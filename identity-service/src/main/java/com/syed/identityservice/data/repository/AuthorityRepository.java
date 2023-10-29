package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    boolean existsByName(String name);
}
