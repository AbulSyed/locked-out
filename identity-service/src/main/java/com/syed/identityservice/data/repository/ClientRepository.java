package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByClientId(String clientId);
}
