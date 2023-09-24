package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.request.UpdateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.domain.model.response.GetUserResponse;
import com.syed.identityservice.domain.model.response.UpdateUserResponse;
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
    private CreateUserRequest createUserRequest;
    private CreateUserResponse createUserResponse;
    private ResponseEntity<CreateUserResponse> createUserExpectedResponse;
    private GetUserResponse getUserResponse;
    private ResponseEntity<GetUserResponse> getUserExpectedResponse;
    private List<GetUserResponse> getUserListResponse;
    private ResponseEntity<List<GetUserResponse>> getUserListExpectedResponse;
    private UpdateUserRequest updateUserRequest;
    private UpdateUserResponse updateUserResponse;
    private ResponseEntity<UpdateUserResponse> updateUserExpectedResponse;

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

        getUserResponse = GetUserResponse.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .createdAt(LocalDateTime.now())
                .build();
        getUserExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getUserResponse);

        getUserListResponse = List.of(GetUserResponse.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .createdAt(LocalDateTime.now())
                .build());
        getUserListExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getUserListResponse);

        updateUserRequest = UpdateUserRequest.builder()
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .build();
        updateUserResponse = UpdateUserResponse.builder()
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
        when(userService.createUser(any(Long.class), any(CreateUserRequest.class))).thenReturn(createUserResponse);

        ResponseEntity<CreateUserResponse> res = userController.createUser(correlationId, 1L, createUserRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createUserExpectedResponse.getBody());
    }

    @Test
    void getUser() {
        when(userService.getUser(any(Long.class))).thenReturn(getUserResponse);

        ResponseEntity<GetUserResponse> res = userController.getUser(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserExpectedResponse.getBody());
    }

    @Test
    void getUserList() {
        when(userService.getUserList()).thenReturn(getUserListResponse);

        ResponseEntity<List<GetUserResponse>> res = userController.getUserList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserListExpectedResponse.getBody());
    }

    @Test
    void updateUser() {
        when(userService.updateUser(any(Long.class), any(UpdateUserRequest.class))).thenReturn(updateUserResponse);

        ResponseEntity<UpdateUserResponse> res = userController.updateUser(correlationId, 1L, updateUserRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), updateUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), updateUserExpectedResponse.getBody());
    }

    @Test
    void deleteUser() {
        doNothing().when(userService).deleteUser(1L);

        userService.deleteUser(1L);
        
        verify(userService, times(1)).deleteUser(1L);
    }
}
