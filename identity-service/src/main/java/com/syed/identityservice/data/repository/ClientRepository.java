package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByClientId(String clientId);
    ClientEntity getClientEntityByUserAppAndClientId(AppEntity userApp, String clientId);
    Page<ClientEntity> getClientEntitiesByUserApp(AppEntity userApp, Pageable pageable);
    Page<ClientEntity> findAllByCreatedAtGreaterThan(LocalDateTime createdAt, Pageable pageable);
    Page<ClientEntity> getClientEntitiesByUserAppAndCreatedAtGreaterThan(AppEntity userApp, LocalDateTime createdAt, Pageable pageable);
}
