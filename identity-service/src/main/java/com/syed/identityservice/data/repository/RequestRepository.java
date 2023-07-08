package com.syed.identityservice.data.repository;

import com.syed.identityservice.data.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
