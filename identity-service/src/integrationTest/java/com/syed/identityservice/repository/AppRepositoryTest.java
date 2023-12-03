package com.syed.identityservice.repository;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// ^ don't configure embedded db, use test containers one
public class AppRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private AppRepository appRepository;

    @BeforeEach
    void setUp() {
        AppEntity appEntity = createAppEntity("app1", "test app", LocalDateTime.now());
        appRepository.save(appEntity);
    }

    @Test
    void existsByNamePositiveTest() {
        boolean existsByName = appRepository.existsByName("app1");

        assertTrue(existsByName);
    }

    @Test
    void existsByNameNegativeTest() {
        boolean existsByName = appRepository.existsByName("app2");

        assertFalse(existsByName);
    }

    @Test
    @Sql(scripts = "classpath:create-app.sql")
    void findByNameTest() {
        AppEntity appEntity = appRepository.findByName("app5"); // created by sql

        assertNotNull(appEntity);
    }

    @Test
    void findByNameNegativeTest() {
        AppEntity appEntity = appRepository.findByName("app2");

        assertNull(appEntity);
    }
}
