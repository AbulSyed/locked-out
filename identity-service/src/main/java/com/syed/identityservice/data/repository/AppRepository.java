package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AppEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppRepository extends JpaRepository<AppEntity, Long> {
    boolean existsByName(String name);
    AppEntity findByName(String name);
    Page<AppEntity> findAllByCreatedAtGreaterThan(LocalDateTime createdAt, Pageable pageable);
}
