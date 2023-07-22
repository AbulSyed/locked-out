package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.AuditRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRequestRepository extends JpaRepository<AuditRequestEntity, Long> {
}
