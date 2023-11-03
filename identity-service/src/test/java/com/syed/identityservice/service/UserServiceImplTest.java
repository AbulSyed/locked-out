package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
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
import static org.mockito.ArgumentMatchers.eq;
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
    private UserRequest createUserRequest;
    private UserRequest updateUserRequest;
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

        createUserRequest = UserRequest.builder()
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();

        updateUserRequest = UserRequest.builder()
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

        UserResponse res = userService.createUser(1L, createUserRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("username", "joe")
                .hasFieldOrPropertyWithValue("password", "123")
                .hasFieldOrPropertyWithValue("email", "joe@mail.com")
                .hasFieldOrPropertyWithValue("phoneNumber", "079");
    }

    @Test
    void getUser() {
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
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserV2Response> res = userService.getUserList();

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void getUserListByApp() {
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(userRepository.getUserEntitiesByUserApp(any(AppEntity.class))).thenReturn(List.of(userEntity));

        List<UserV2Response> res = userService.getUserListByApp(1L, null);

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void updateUser() {
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
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(userEntity));

        userService.deleteUser(1L);

        verify(userRepository).delete(userEntity);
    }
}
