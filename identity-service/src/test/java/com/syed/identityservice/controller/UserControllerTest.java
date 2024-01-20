package com.syed.identityservice.controller;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2PageResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
import com.syed.identityservice.service.UserService;
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
class UserControllerTest extends BaseTest<Object> {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser() {
        UserRequest createUserRequest = createUserRequest("joe", "123", "joe@mail.com", "079");
        UserResponse createUserResponse = createUserResponse(1L, "joe", "123", "joe@mail.com", "079", LocalDateTime.now());
        ResponseEntity<Object> createUserExpectedResponse = createExpectedResponse(HttpStatus.CREATED, createUserResponse);

        when(userService.createUser(any(Long.class), any(UserRequest.class))).thenReturn(createUserResponse);

        ResponseEntity<UserResponse> res = userController.createUser(correlationId, 1L, createUserRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createUserExpectedResponse.getBody());
    }

    @Test
    void getUser() {
        UserV2Response getUserResponse = createUserV2Response(1L, "joe", "123", "joe@mail.com", "079", LocalDateTime.now());
        ResponseEntity<Object> getUserExpectedResponse = createExpectedResponse(HttpStatus.OK, getUserResponse);

        when(userService.getUser(any(Long.class), eq(null), eq(null))).thenReturn(getUserResponse);

        ResponseEntity<UserV2Response> res = userController.getUser(correlationId, 1L, null, null);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserExpectedResponse.getBody());
    }

    @Test
    void getUserList() {
        List<UserV2Response> getUserListResponse = List.of(
                createUserV2Response(1L, "joe", "123", "joe@mail.com", "079", LocalDateTime.now())
        );
        UserV2PageResponse userV2PageResponse = createUserV2PageResponse(getUserListResponse, 1, 10, 5L, 1, true);
        ResponseEntity<Object> getUserListExpectedResponse = createExpectedResponse(HttpStatus.OK, userV2PageResponse);

        when(userService.getUserList(any(Integer.class), eq(null))).thenReturn(userV2PageResponse);

        ResponseEntity<UserV2PageResponse> res = userController.getUserList(correlationId, 10, null);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserListExpectedResponse.getBody());
    }

    @Test
    void getUserListByApp() {
        List<UserV2Response> getUserListResponse = List.of(
                createUserV2Response(1L, "joe", "123", "joe@mail.com", "079", LocalDateTime.now())
        );
        UserV2PageResponse userV2PageResponse = createUserV2PageResponse(getUserListResponse, 1, 10, 5L, 1, true);
        ResponseEntity<Object> getUserListExpectedResponse = createExpectedResponse(HttpStatus.OK, userV2PageResponse);

        when(userService.getUserListByApp(any(Long.class), eq(null), any(Integer.class), eq(null))).thenReturn(userV2PageResponse);

        ResponseEntity<UserV2PageResponse> res = userController.getUserListByApp(correlationId, 1L, null, 10, null);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getUserListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getUserListExpectedResponse.getBody());
    }

    @Test
    void updateUser() {
        UserRequest updateUserRequest = createUserRequest("joe2", "pw", "joe2@mail.com", "079");
        UserResponse updateUserResponse = createUserResponse(1L, "joe2", "pw", "joe2@mail.com", "079", LocalDateTime.now());
        ResponseEntity<Object> updateUserExpectedResponse = createExpectedResponse(HttpStatus.OK, updateUserResponse);

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
