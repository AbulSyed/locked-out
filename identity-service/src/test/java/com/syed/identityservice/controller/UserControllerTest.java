package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private String correlationId;
    private UserRequest createUserRequest;
    private UserResponse createUserResponse;
    private ResponseEntity<UserResponse> createUserExpectedResponse;
    private UserV2Response getUserResponse;
    private ResponseEntity<UserV2Response> getUserExpectedResponse;
    private List<UserV2Response> getUserListResponse;
    private ResponseEntity<List<UserV2Response>> getUserListExpectedResponse;
    private UserRequest updateUserRequest;
    private UserResponse updateUserResponse;
    private ResponseEntity<UserResponse> updateUserExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createUserRequest = UserRequest.builder()
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();
        createUserResponse = UserResponse.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .createdAt(LocalDateTime.now())
                .build();
        createUserExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createUserResponse);

        getUserResponse = UserV2Response.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .createdAt(LocalDateTime.now())
                .build();
        getUserExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getUserResponse);

        getUserListResponse = List.of(UserV2Response.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .createdAt(LocalDateTime.now())
                .build());
        getUserListExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getUserListResponse);

        updateUserRequest = UserRequest.builder()
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();
        updateUserResponse = UserResponse.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();
        updateUserExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(updateUserResponse);
    }

    @Test
    void createUser() {
        when(userService.createUser(any(Long.class), any(UserRequest.class))).thenReturn(createUserResponse);

        ResponseEntity<UserResponse> res = userController.createUser(correlationId, 1L, createUserRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createUserExpectedResponse.getBody());
    }

    @Test
    void getUser() {
        when(userService.getUser(any(Long.class), eq(null), eq(null))).thenReturn(getUserResponse);

        ResponseEntity<UserV2Response> res = userController.getUser(correlationId, 1L, null, null);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserExpectedResponse.getBody());
    }

    @Test
    void getUserList() {
        when(userService.getUserList()).thenReturn(getUserListResponse);

        ResponseEntity<List<UserV2Response>> res = userController.getUserList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserListExpectedResponse.getBody());
    }

    @Test
    void getUserListByApp() {
        when(userService.getUserListByApp(any(Long.class), eq(null))).thenReturn(getUserListResponse);

        ResponseEntity<List<UserV2Response>> res = userController.getUserListByApp(correlationId, 1L, null);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserListExpectedResponse.getBody());
    }

    @Test
    void updateUser() {
        when(userService.updateUser(any(Long.class), any(UserRequest.class))).thenReturn(updateUserResponse);

        ResponseEntity<UserResponse> res = userController.updateUser(correlationId, 1L, updateUserRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), updateUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), updateUserExpectedResponse.getBody());
    }

    @Test
    void deleteUser() {
        userController.deleteUser(correlationId, any(Long.class));

        verify(userService, times(1)).deleteUser(any(Long.class));
    }
}
