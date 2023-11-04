package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;
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
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AppServiceImplTest {

    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private AppServiceImpl appService;

    private AppRequest createAppRequest;
    private AppEntity appEntity;
    private AppEntity appV2Entity;
    private UserEntity userEntity;
    private ClientEntity clientEntity;
    private LocalDateTime createdAt;
    private List<AppEntity> appEntityList;
    private AppRequest updateAppRequest;
    private AppEntity updatedAppEntity;

    @BeforeEach
    void setUp() {
        createdAt = LocalDateTime.now();

        createAppRequest = AppRequest.builder()
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
        clientEntity = ClientEntity.builder()
                .id(1L)
                .clientId("abc")
                .secret("secret")
                .roles(Collections.emptySet())
                .authorities(Collections.emptySet())
                .build();

        appV2Entity = AppEntity.builder()
                .id(1L)
                .name("app")
                .description("desc")
                .users(Set.of(userEntity))
                .clients(Set.of(clientEntity))
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

        updateAppRequest = AppRequest.builder()
                .name("new name")
                .description("new desc")
                .build();

        updatedAppEntity = AppEntity.builder()
                .id(1L)
                .name("new name")
                .description("new desc")
                .createdAt(createdAt)
                .build();
    }

    @Test
    void createApp_Success() {
        when(appRepository.existsByName("app")).thenReturn(false);
        when(appRepository.save(any(AppEntity.class))).thenReturn(appEntity);

        AppResponse res = appService.createApp(createAppRequest);

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

        assertEquals("Name field is already in use, please try another value", throwable.getMessage());
    }

    @Test
    void getApp_Success() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appEntity));

        AppResponse res = appService.getApp(1L);

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
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appV2Entity));

        AppV2Response res = appService.getAppV2(1L);

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

        List<AppResponse> res = appService.getAppList();

        assertThat(res)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void updateApp() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appEntity));
        when(appRepository.existsByName("new name")).thenReturn(false);
        when(appRepository.save(any(AppEntity.class))).thenReturn(updatedAppEntity);

        AppResponse res = appService.updateApp(1L, updateAppRequest);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "new name")
                .hasFieldOrPropertyWithValue("description", "new desc")
                .hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    void deleteApp() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appEntity));

        appService.deleteApp(1L);

        verify(appRepository).delete(appEntity);
    }
}
