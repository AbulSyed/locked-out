package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private String correlationId;
    private CreateUserRequest createUserRequest;
    private CreateUserResponse createUserResponse;
    private ResponseEntity<CreateUserResponse> createUserExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createUserRequest = CreateUserRequest.builder()
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();
        createUserResponse = CreateUserResponse.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .createdAt(LocalDateTime.now())
                .build();
        createUserExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);
    }

    @Test
    void createUser() {
        when(userService.createUser(any(Long.class), any(CreateUserRequest.class))).thenReturn(createUserResponse);

        ResponseEntity<CreateUserResponse> res = userController.createUser(correlationId, 1L, createUserRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createUserExpectedResponse.getBody());
    }
}
