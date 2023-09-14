package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.GetAppDetailsResponse;
import com.syed.identityservice.domain.model.response.GetAppResponse;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.impl.AppServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AppServiceImplTest {

    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private AppServiceImpl appService;

    private CreateAppRequest createAppRequest;
    private AppEntity appEntity;
    private AppEntity appDetailsEntity;
    private UserEntity userEntity;
    private LocalDateTime createdAt;
    private List<AppEntity> appEntityList;

    @BeforeEach
    void setUp() {
        createdAt = LocalDateTime.now();

        createAppRequest = CreateAppRequest.builder()
                .name("app")
                .description("desc")
                .build();

        appEntity = AppEntity.builder()
                .id(1L)
                .name("app")
                .description("desc")
                .createdAt(createdAt)
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username("test")
                .password("test")
                .email("a@m.com")
                .phoneNumber("123")
                .roles(Collections.emptySet())
                .authorities(Collections.emptySet())
                .createdAt(createdAt)
                .build();

        appDetailsEntity = AppEntity.builder()
                .id(1L)
                .name("app")
                .description("desc")
                .users(Set.of(userEntity))
                .createdAt(createdAt)
                .build();

        appEntityList = List.of(
                new AppEntity(
                        1L,
                        "app",
                        "test",
                        new HashSet<>(),
                        new HashSet<>(),
                        LocalDateTime.now()
                )
        );
    }

    @Test
    void createApp_Success() {
        when(appRepository.existsByName("app")).thenReturn(false);
        when(appRepository.save(any(AppEntity.class))).thenReturn(appEntity);

        CreateAppResponse res = appService.createApp(createAppRequest);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "app")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    void createApp_ThrowsFieldAlreadyExistsException() {
        when(appRepository.existsByName("app")).thenReturn(true);

        Throwable throwable = assertThrows(FieldAlreadyExistsException.class, () -> appService.createApp(createAppRequest));

        assertEquals("Name field is already in use, please try another name", throwable.getMessage());
    }

    @Test
    void getApp_Success() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appEntity));

        GetAppResponse res = appService.getApp(1L);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "app")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    void getApp_ThrowsResourceNotFoundException() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> appService.getApp(1L));

        assertEquals("App with id 1 not found", throwable.getMessage());
    }

    @Test
    void getAppV2_Success() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appDetailsEntity));

        GetAppDetailsResponse res = appService.getAppV2(1L);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "app")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    void getAppList_Success() {
        when(appRepository.findAll()).thenReturn(appEntityList);

        List<GetAppResponse> res = appService.getAppList();

        assertThat(res)
                .isNotNull()
                .hasSize(1);
    }
}
