package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.service.impl.UserServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
                .createdAt(LocalDateTime.now())
                .build();

        createUserRequest = CreateUserRequest.builder()
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
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
}
