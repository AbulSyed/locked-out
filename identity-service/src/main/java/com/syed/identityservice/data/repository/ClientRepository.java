package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByClientId(String clientId);
    ClientEntity getByClientId(String clientId);
    ClientEntity getClientEntityByUserAppAndClientId(AppEntity userApp, String clientId);
    List<ClientEntity> getClientEntitiesByUserApp(AppEntity userApp);
}
