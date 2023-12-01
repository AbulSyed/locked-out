package com.syed.identityservice.controller;

import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.request.ScopeRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.service.ScopeService;
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
class ScopeControllerTest extends ControllerBaseTest<Object> {

    @Mock
    private ScopeService scopeService;

    @InjectMocks
    private ScopeController scopeController;

    @Test
    void alterClientScopesTest() {
        ScopeRequest alterClientScopesRequest = createScopeRequest(Set.of(ScopeEnum.OPENID));
        MessageResponse alterClientScopesResponse = createMessageResponse("Client 1 updated with scopes [openid]");
        ResponseEntity<Object> alterClientScopesExpectedResponse = createExpectedResponse(HttpStatus.OK, alterClientScopesResponse);

        when(scopeService.alterClientScopes(any(Long.class), any(ScopeRequest.class))).thenReturn(alterClientScopesResponse);

        ResponseEntity<MessageResponse> res = scopeController.alterClientScopes(correlationId, 1L, alterClientScopesRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), alterClientScopesExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), alterClientScopesExpectedResponse.getBody());
    }
}
