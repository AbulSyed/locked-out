package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.service.AuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorityControllerTest {

    @Mock
    private AuthorityService authorityService;

    @InjectMocks
    private AuthorityController authorityController;

    private String correlationId;
    private AuthorityRequest createAuthorityRequest;
    private AuthorityResponse createAuthorityResponse;
    private ResponseEntity<AuthorityResponse> createAuthorityExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createAuthorityRequest = AuthorityRequest.builder()
                .name("read")
                .build();
        createAuthorityResponse = AuthorityResponse.builder()
                .id(1L)
                .name("read")
                .build();
        createAuthorityExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createAuthorityResponse);
    }

    @Test
    void createAuthority() {
        when(authorityService.createAuthority(any(AuthorityRequest.class))).thenReturn(createAuthorityResponse);

        ResponseEntity<AuthorityResponse> res = authorityController.createAuthority(correlationId, createAuthorityRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createAuthorityExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createAuthorityExpectedResponse.getBody());
    }
}
