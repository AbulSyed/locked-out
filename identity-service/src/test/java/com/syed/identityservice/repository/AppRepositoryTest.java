package com.syed.identityservice.repository;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void existsByName_Positive() {
        appRepository.save(app);

        boolean state = appRepository.existsByName("test");

        assertTrue(state);
    }

    @Test
    void existsByName_Negative() {
        appRepository.save(app);

        boolean state = appRepository.existsByName("negative");

        assertFalse(state);
    }
}
