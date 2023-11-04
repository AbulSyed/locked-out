package com.syed.identityservice.controller;

import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.request.ScopeRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.service.ScopeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScopeControllerTest {

    @Mock
    private ScopeService scopeService;

    @InjectMocks
    private ScopeController scopeController;

    private String correlationId;
    private ScopeRequest alterClientScopesRequest;
    private MessageResponse alterClientScopesResponse;
    private ResponseEntity<MessageResponse> alterClientScopesExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        alterClientScopesRequest = ScopeRequest
                .builder()
                .scopes(Set.of(ScopeEnum.OPENID))
                .build();
        alterClientScopesResponse = MessageResponse.builder()
                .message("Client 1 updated with scopes [openid]")
                .build();
        alterClientScopesExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(alterClientScopesResponse);
    }

    @Test
    void alterClientScopesTest() {
        when(scopeService.alterClientScopes(any(Long.class), any(ScopeRequest.class))).thenReturn(alterClientScopesResponse);

        ResponseEntity<MessageResponse> res = scopeController.alterClientScopes(correlationId, 1L, alterClientScopesRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), alterClientScopesExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), alterClientScopesExpectedResponse.getBody());
    }
}
