package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.service.impl.AppServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class AppServiceImplTest {

    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private AppServiceImpl appService;

    private CreateAppRequest createAppRequest;
    private AppEntity appEntity;
    private LocalDateTime createdAt;

    @BeforeEach
    void setUp() {
        createdAt = LocalDateTime.now();

        createAppRequest = CreateAppRequest.builder()
                .name("app")
                .build();

        appEntity = AppEntity.builder()
                .id(1L)
                .name("app")
                .createdAt(createdAt)
                .build();
    }

    @Test
    void createApp_Success() {
        when(appRepository.existsByName("app")).thenReturn(false);
        when(appRepository.save(any())).thenReturn(appEntity);

        CreateAppResponse res = appService.createApp(createAppRequest);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "app")
                .hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    void createApp_ThrowsAlreadyExistsException() {
        when(appRepository.existsByName("app")).thenReturn(true);

        assertThrows(FieldAlreadyExistsException.class, () -> appService.createApp(createAppRequest));
    }
}
