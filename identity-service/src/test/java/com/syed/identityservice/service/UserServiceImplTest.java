package com.syed.identityservice.service;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2PageResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
import com.syed.identityservice.service.impl.UserServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import com.syed.identityservice.utility.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends BaseTest<Object> {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                                                    Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());
        UserRequest createUserRequest = createUserRequest("joe", "123", "joe@mail.com", "079");

        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserResponse res = userService.createUser(1L, createUserRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("username", "joe")
                .hasFieldOrPropertyWithValue("password", "123")
                .hasFieldOrPropertyWithValue("email", "joe@mail.com")
                .hasFieldOrPropertyWithValue("phoneNumber", "079");
    }

    @Test
    void getUser() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                                                    Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        UserV2Response res = userService.getUser(1L, null, null);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("username", "joe")
                .hasFieldOrPropertyWithValue("password", "123")
                .hasFieldOrPropertyWithValue("email", "joe@mail.com")
                .hasFieldOrPropertyWithValue("phoneNumber", "079");
    }

    @Test
    @SuppressWarnings("unchecked")
    void getUserList() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());

        Pageable pageable = Utility.createPageable(1, 10, Sort.by(Sort.DEFAULT_DIRECTION, "createdAt"));

        Page<UserEntity> userEntityPage = mock(Page.class);

        when(userRepository.findAll(pageable)).thenReturn(userEntityPage);

        when(userEntityPage.getContent()).thenReturn(List.of(userEntity));
        when(userEntityPage.getNumber()).thenReturn(0);
        when(userEntityPage.getSize()).thenReturn(10);
        when(userEntityPage.getTotalElements()).thenReturn(1L);
        when(userEntityPage.getTotalPages()).thenReturn(1);
        when(userEntityPage.isLast()).thenReturn(true);

        UserV2PageResponse res = userService.getUserList(10, null);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("page", 1)
                .hasFieldOrPropertyWithValue("size", 10)
                .hasFieldOrPropertyWithValue("totalElements", 1L)
                .hasFieldOrPropertyWithValue("totalPages", 1)
                .hasFieldOrPropertyWithValue("lastPage", true);
    }

    @Test
    @SuppressWarnings("unchecked")
    void getUserListByApp() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());

        Pageable pageable = Utility.createPageable(1, 10, Sort.by(Sort.DEFAULT_DIRECTION, "createdAt"));

        Page<UserEntity> userEntityPage = mock(Page.class);

        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(userRepository.getUserEntitiesByUserApp(any(AppEntity.class), eq(pageable))).thenReturn(userEntityPage);

        when(userEntityPage.getContent()).thenReturn(List.of(userEntity));
        when(userEntityPage.getNumber()).thenReturn(0);
        when(userEntityPage.getSize()).thenReturn(10);
        when(userEntityPage.getTotalElements()).thenReturn(1L);
        when(userEntityPage.getTotalPages()).thenReturn(1);
        when(userEntityPage.isLast()).thenReturn(true);

        UserV2PageResponse res = userService.getUserListByApp(1L, null, 10, null);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("page", 1)
                .hasFieldOrPropertyWithValue("size", 10)
                .hasFieldOrPropertyWithValue("totalElements", 1L)
                .hasFieldOrPropertyWithValue("totalPages", 1)
                .hasFieldOrPropertyWithValue("lastPage", true);
    }

    @Test
    void updateUser() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());
        UserRequest updateUserRequest = createUserRequest("new username", "new username", "joe@mail.com", "079");
        UserEntity updatedUserEntity = createUserEntity(1L, "new username", "123", "joe@mail.com", "079", appEntity,
                Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(userEntity));
        when(userRepository.existsByUsername("new username")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        UserResponse res = userService.updateUser(1L, updateUserRequest);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("username", "new username");
    }

    @Test
    void deleteUser() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(userEntity));

        userService.deleteUser(1L);

        verify(userRepository).delete(userEntity);
    }
}
