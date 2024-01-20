package com.syed.identityservice.service;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.response.AppPageResponse;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.impl.AppServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import com.syed.identityservice.utility.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class AppServiceImplTest extends BaseTest<Object> {

    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private AppServiceImpl appService;

    @Test
    void createApp_Success() {
        LocalDateTime createdAt = LocalDateTime.now();
        AppRequest createAppRequest = createAppRequest("app", "desc");
        AppEntity appEntity = createAppEntity(1L, "app", "desc", createdAt);

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
        AppRequest createAppRequest = createAppRequest("app", "desc");

        when(appRepository.existsByName("app")).thenReturn(true);

        Throwable throwable = assertThrows(FieldAlreadyExistsException.class, () -> appService.createApp(createAppRequest));

        assertEquals("Name field is already in use, please try another value", throwable.getMessage());
    }

    @Test
    void getApp_Success() {
        LocalDateTime createdAt = LocalDateTime.now();
        AppEntity appEntity = createAppEntity(1L, "app", "desc", createdAt);

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
        LocalDateTime createdAt = LocalDateTime.now();
        UserEntity userEntity = createUserEntity(1L, "test", "test", "a@m.com", "123", Collections.emptySet(), Collections.emptySet(), createdAt);
        ClientEntity clientEntity = createClientEntity(1L, "abc", "secret", Collections.emptySet(), Collections.emptySet(), createdAt);
        AppEntity appV2Entity = createAppEntity(1L, "app", "desc", Set.of(userEntity), Set.of(clientEntity), createdAt);

        when(appRepository.findByName(any(String.class))).thenReturn(appV2Entity);

        AppV2Response res = appService.getAppV2("test");

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "app")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAppList_Success() {
        List<AppEntity> appEntityList = List.of(
                createAppEntity(1L,"app","test", new HashSet<>(), new HashSet<>(), LocalDateTime.now())
        );
        Pageable pageable = Utility.createPageable(1, 10, Sort.by(Sort.DEFAULT_DIRECTION, "createdAt"));

        Page<AppEntity> appEntityPage = mock(Page.class);

        when(appRepository.findAll(pageable)).thenReturn(appEntityPage);

        when(appEntityPage.getContent()).thenReturn(appEntityList);
        when(appEntityPage.getNumber()).thenReturn(0);
        when(appEntityPage.getSize()).thenReturn(10);
        when(appEntityPage.getTotalElements()).thenReturn(1L);
        when(appEntityPage.getTotalPages()).thenReturn(1);
        when(appEntityPage.isLast()).thenReturn(true);

        AppPageResponse res = appService.getAppList(10, null);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("page", 1)
                .hasFieldOrPropertyWithValue("size", 10)
                .hasFieldOrPropertyWithValue("totalElements", 1L)
                .hasFieldOrPropertyWithValue("totalPages", 1)
                .hasFieldOrPropertyWithValue("lastPage", true);
    }

    @Test
    void updateApp() {
        LocalDateTime createdAt = LocalDateTime.now();
        AppEntity appEntity = createAppEntity(1L, "app", "desc", createdAt);
        AppRequest updateAppRequest = createAppRequest("updated name", "updated desc");
        AppEntity updatedAppEntity = createAppEntity(1L, "updated name", "updated desc", createdAt);

        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appEntity));
        when(appRepository.existsByName("updated name")).thenReturn(false);
        when(appRepository.save(any(AppEntity.class))).thenReturn(updatedAppEntity);

        AppResponse res = appService.updateApp(1L, updateAppRequest);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "updated name")
                .hasFieldOrPropertyWithValue("description", "updated desc")
                .hasFieldOrPropertyWithValue("createdAt", createdAt);
    }

    @Test
    void deleteApp() {
        LocalDateTime createdAt = LocalDateTime.now();
        AppEntity appEntity = createAppEntity(1L, "app", "desc", createdAt);

        when(appRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(appEntity));

        appService.deleteApp(1L);

        verify(appRepository).delete(appEntity);
    }
}
