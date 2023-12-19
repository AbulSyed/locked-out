package com.syed.identityservice.controller;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.domain.enums.AuthorityToEnum;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.service.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorityControllerTest extends BaseTest<Object> {

    @Mock
    private AuthorityService authorityService;

    @InjectMocks
    private AuthorityController authorityController;

    @Test
    void createAuthority() {
        AuthorityRequest createAuthorityRequest = createAuthorityRequest("read");
        AuthorityResponse createAuthorityResponse = createAuthorityResponse(1L, "read");
        ResponseEntity<Object> createAuthorityExpectedResponse = createExpectedResponse(HttpStatus.CREATED, createAuthorityResponse);

        when(authorityService.createAuthority(any(AuthorityRequest.class))).thenReturn(createAuthorityResponse);

        ResponseEntity<AuthorityResponse> res = authorityController.createAuthority(correlationId, createAuthorityRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createAuthorityExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createAuthorityExpectedResponse.getBody());
    }

    @Test
    void addAuthority_ToUser() {
        MessageResponse addAuthorityResponse = createMessageResponse("Authority read added to user Test");
        ResponseEntity<Object> addAuthorityExpectedResponse = createExpectedResponse(HttpStatus.CREATED, addAuthorityResponse);

        when(authorityService.addAuthority(any(AuthorityToEnum.class), any(Long.class), any(Long.class))).thenReturn(addAuthorityResponse);

        ResponseEntity<MessageResponse> res = authorityController.addAuthority(correlationId, AuthorityToEnum.USER, 1L, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), addAuthorityExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), addAuthorityExpectedResponse.getBody());
    }

    @Test
    void getAuthorityList() {
        List<AuthorityResponse> getAuthorityListResponse = List.of(new AuthorityResponse(1L, "read"));
        ResponseEntity<Object> getAuthorityListExpectedResponse = createExpectedResponse(HttpStatus.OK, getAuthorityListResponse);

        when(authorityService.getAuthorityList()).thenReturn(getAuthorityListResponse);

        ResponseEntity<List<AuthorityResponse>> res = authorityController.getAuthorityList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAuthorityListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAuthorityListExpectedResponse.getBody());
    }

    @Test
    void deleteAuthorityFrom_User() {
        authorityController.deleteAuthorityFrom(correlationId, AuthorityToEnum.USER, 1L, 1L);

        verify(authorityService, times(1)).deleteAuthorityFrom(AuthorityToEnum.USER, 1L, 1L);
    }

    @Test
    void deleteAuthority() {
        authorityController.deleteAuthority(correlationId, 1L);

        verify(authorityService, times(1)).deleteAuthority(1L);
    }
}
