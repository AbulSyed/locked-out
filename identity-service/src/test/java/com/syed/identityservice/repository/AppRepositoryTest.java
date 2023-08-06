package com.syed.identityservice.repository;

import com.syed.identityservice.data.repository.AppRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppRepositoryTest {

    @Mock
    private AppRepository appRepository;

    @Test
    void existsByName_ReturnTrue() {
        when(appRepository.existsByName("app")).thenReturn(true);

        boolean state = appRepository.existsByName("app");

        assertTrue(state);
    }

    @Test
    void existsByName_ReturnFalse() {
        when(appRepository.existsByName("app")).thenReturn(false);

        boolean state = appRepository.existsByName("app");

        assertFalse(state);
    }
}
