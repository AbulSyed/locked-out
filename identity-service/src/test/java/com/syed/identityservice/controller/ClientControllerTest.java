package com.syed.identityservice.controller;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientPageResponse;
import com.syed.identityservice.domain.model.response.ClientResponse;
import com.syed.identityservice.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest extends BaseTest<Object> {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Test
    void createClient() {
        ClientRequest createClientRequest = createClientRequest("client id", "secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000");
        ClientResponse createClientResponse = createClientResponse(1L, "client id", "secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000", LocalDateTime.now());
        ResponseEntity<Object> createClientExpectedResponse = createExpectedResponse(HttpStatus.CREATED, createClientResponse);

        when(clientService.createClient(any(Long.class), any(ClientRequest.class))).thenReturn(createClientResponse);

        ResponseEntity<ClientResponse> res = clientController.createClient(correlationId, 1L, createClientRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createClientExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createClientExpectedResponse.getBody());
    }

    @Test
    void getClient() {
        ClientResponse getClientResponse = createClientResponse(1L, "client id", "secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000", LocalDateTime.now());
        ResponseEntity<Object> getClientExpectedResponse = createExpectedResponse(HttpStatus.OK, getClientResponse);

        when(clientService.getClient(any(Long.class), eq(null), eq(null))).thenReturn(getClientResponse);

        ResponseEntity<ClientResponse> res = clientController.getClient(correlationId, 1L, null, null);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getClientExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getClientExpectedResponse.getBody());
    }

    @Test
    void getClientList() {
        List<ClientResponse> getClientListResponse = List.of(
                createClientResponse(1L, "client id", "secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000", LocalDateTime.now())
        );
        ClientPageResponse clientPageResponse = createClientPageResponse(getClientListResponse, 1, 10, 5L, 1, true);
        ResponseEntity<Object> getClientListExpectedResponse = createExpectedResponse(HttpStatus.OK, clientPageResponse);

        when(clientService.getClientList(1, 10)).thenReturn(clientPageResponse);

        ResponseEntity<ClientPageResponse> res = clientController.getClientList(correlationId, 1, 10);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getClientListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getClientListExpectedResponse.getBody());
    }

    @Test
    void getClientListByApp() {
        List<ClientResponse> getClientListResponse = List.of(
                createClientResponse(1L, "client id", "secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000", LocalDateTime.now())
        );
        ClientPageResponse clientPageResponse = createClientPageResponse(getClientListResponse, 1, 10, 5L, 1, true);
        ResponseEntity<Object> getClientListExpectedResponse = createExpectedResponse(HttpStatus.OK, clientPageResponse);

        when(clientService.getClientListByApp(any(Long.class), eq(null), any(Integer.class), any(Integer.class))).thenReturn(clientPageResponse);

        ResponseEntity<ClientPageResponse> res = clientController.getClientListByApp(correlationId, 1L, null, 1, 10);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getClientListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getClientListExpectedResponse.getBody());
    }

    @Test
    void updateClient() {
        ClientRequest updateClientRequest = createClientRequest("new client id", "new secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000");
        ClientResponse updateClientResponse = createClientResponse(1L, "new client id", "new secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000", LocalDateTime.now());
        ResponseEntity<Object> updateClientExpectedResponse = createExpectedResponse(HttpStatus.OK, updateClientResponse);

        when(clientService.updateClient(any(Long.class), any(ClientRequest.class))).thenReturn(updateClientResponse);

        ResponseEntity<ClientResponse> res = clientController.updateClient(correlationId, 1L, updateClientRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), updateClientExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), updateClientExpectedResponse.getBody());
    }

    @Test
    void deleteClient() {
        clientController.deleteClient(correlationId, any(Long.class));

        verify(clientService, times(1)).deleteClient(any(Long.class));
    }
}
