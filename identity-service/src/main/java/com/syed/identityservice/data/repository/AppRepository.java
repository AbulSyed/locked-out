package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AppEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<AppEntity, Long> {
    boolean existsByName(String name);
    AppEntity findByName(String name);
    @Override
    Page<AppEntity> findAll(Pageable pageable);
}
