package com.syed.identityservice.service;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
import com.syed.identityservice.service.impl.UserServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void getUserList() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());

        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserV2Response> res = userService.getUserList();

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void getUserListByApp() {
        AppEntity appEntity = createAppEntity(1L, "app", "desc", LocalDateTime.now());
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079", appEntity,
                Collections.emptySet(), Collections.emptySet(), LocalDateTime.now());

        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(userRepository.getUserEntitiesByUserApp(any(AppEntity.class))).thenReturn(List.of(userEntity));

        List<UserV2Response> res = userService.getUserListByApp(1L, null);

        assertThat(res).isNotNull()
                .hasSize(1);
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
