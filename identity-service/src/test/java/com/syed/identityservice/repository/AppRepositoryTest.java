package com.syed.identityservice.repository;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class AppRepositoryTest {

    @Autowired
    private AppRepository appRepository;

    private AppEntity app;

    @BeforeEach
    void setUp() {
        app = AppEntity.builder()
                .name("test")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void existsByNameTestPositive() {
        appRepository.save(app);

        boolean state = appRepository.existsByName("test");

        Assertions.assertTrue(state);
    }

    @Test
    void existsByNameTestNegative() {
        appRepository.save(app);

        boolean state = appRepository.existsByName("negative");

        Assertions.assertFalse(state);
    }
}
