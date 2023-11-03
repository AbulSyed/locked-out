package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    UserEntity getUserEntityByUserAppAndUsername(AppEntity userApp, String username);
    List<UserEntity> getUserEntitiesByUserApp(AppEntity userApp);
}
