package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    UserEntity getUserEntityByUserAppAndUsername(AppEntity userApp, String username);
    Page<UserEntity> getUserEntitiesByUserApp(AppEntity userApp, Pageable pageable);
    Page<UserEntity> findAllByCreatedAtGreaterThan(LocalDateTime createdAt, Pageable pageable);
    Page<UserEntity> getUserEntitiesByUserAppAndCreatedAtGreaterThan(AppEntity userApp, LocalDateTime createdAt, Pageable pageable);
}
