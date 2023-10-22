package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.request.UpdateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.domain.model.response.GetUserResponse;
import com.syed.identityservice.domain.model.response.UpdateUserResponse;
import com.syed.identityservice.service.impl.UserServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
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
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private AppEntity appEntity;
    private UserEntity userEntity;
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;
    private UserEntity updatedUserEntity;

    @BeforeEach
    void setUp() {
        appEntity = AppEntity.builder()
                .id(1L)
                .name("app")
                .description("desc")
                .createdAt(LocalDateTime.now())
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .userApp(appEntity)
                .roles(Collections.emptySet())
                .authorities(Collections.emptySet())
                .createdAt(LocalDateTime.now())
                .build();

        createUserRequest = CreateUserRequest.builder()
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();

        updateUserRequest = UpdateUserRequest.builder()
                .username("new username")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();

        updatedUserEntity = UserEntity.builder()
                .id(1L)
                .username("new username")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .userApp(appEntity)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createUser() {
        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        CreateUserResponse res = userService.createUser(1L, createUserRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("username", "joe")
                .hasFieldOrPropertyWithValue("password", "123")
                .hasFieldOrPropertyWithValue("email", "joe@mail.com")
                .hasFieldOrPropertyWithValue("phoneNumber", "079");
    }

    @Test
    void getUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        GetUserResponse res = userService.getUser(1L);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("username", "joe")
                .hasFieldOrPropertyWithValue("password", "123")
                .hasFieldOrPropertyWithValue("email", "joe@mail.com")
                .hasFieldOrPropertyWithValue("phoneNumber", "079");
    }

    @Test
    void getUserList() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<GetUserResponse> res = userService.getUserList();

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void getUserListByAppId() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(userRepository.getUserEntitiesByUserApp(any(AppEntity.class))).thenReturn(List.of(userEntity));

        List<GetUserResponse> res = userService.getUserListByAppId(1L);

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void updateUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(userEntity));
        when(userRepository.existsByUsername("new username")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        UpdateUserResponse res = userService.updateUser(1L, updateUserRequest);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("username", "new username");
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(userEntity));

        userService.deleteUser(1L);

        verify(userRepository).delete(userEntity);
    }
}
