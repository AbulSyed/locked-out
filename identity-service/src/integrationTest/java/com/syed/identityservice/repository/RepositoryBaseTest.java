package com.syed.identityservice.repository;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.AppEntity;

import java.time.LocalDateTime;

public class RepositoryBaseTest extends BaseTest {

    protected AppEntity createAppEntity(String name, String description, LocalDateTime createdAt) {
        return AppEntity.builder()
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .build();
    }
}
