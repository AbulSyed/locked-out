package com.syed.identityservice.controller;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.request.UpdateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.domain.model.response.GetClientResponse;
import com.syed.identityservice.domain.model.response.UpdateClientResponse;
import com.syed.identityservice.service.ClientService;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private String correlationId;
    private CreateClientRequest createClientRequest;
    private CreateClientResponse createClientResponse;
    private ResponseEntity<CreateClientResponse> createClientExpectedResponse;
    private GetClientResponse getClientResponse;
    private ResponseEntity<GetClientResponse> getClientExpectedResponse;
    private List<GetClientResponse> getClientListResponse;
    private ResponseEntity<List<GetClientResponse>> getClientListExpectedResponse;
    private UpdateClientResponse updateClientResponse;
    private UpdateClientRequest updateClientRequest;
    private ResponseEntity<UpdateClientResponse> updateClientExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createClientRequest = CreateClientRequest.builder()
                .clientId("1")
                .clientSecret("secret")
                .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                .redirectUri("http://localhost:3000")
                .build();
        createClientResponse = CreateClientResponse.builder()
                .id(1L)
                .clientId("1")
                .clientSecret("secret")
                .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                .redirectUri("http://localhost:3000")
                .createdAt(LocalDateTime.now())
                .build();
        createClientExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createClientResponse);

        getClientResponse = GetClientResponse.builder()
                .id(1L)
                .clientId("1")
                .clientSecret("secret")
                .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                .redirectUri("http://localhost:3000")
                .createdAt(LocalDateTime.now())
                .build();
        getClientExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getClientResponse);

        getClientListResponse = List.of(
                GetClientResponse.builder()
                        .id(1L)
                        .clientId("1")
                        .clientSecret("secret")
                        .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                        .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                        .redirectUri("http://localhost:3000")
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        getClientListExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getClientListResponse);

        updateClientResponse = UpdateClientResponse.builder()
                .clientId("new client id")
                .clientSecret("secret")
                .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                .redirectUri("http://localhost:3000")
                .build();
        updateClientRequest = UpdateClientRequest.builder()
                .clientId("new client id")
                .clientSecret("secret")
                .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                .redirectUri("http://localhost:3000")
                .build();
        updateClientExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(updateClientResponse);
    }

    @Test
    void createClient() {
        when(clientService.createClient(any(Long.class), any(CreateClientRequest.class))).thenReturn(createClientResponse);

        ResponseEntity<CreateClientResponse> res = clientController.createClient(correlationId, 1L, createClientRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createClientExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createClientExpectedResponse.getBody());
    }

    @Test
    void getClient() {
        when(clientService.getClient(any(Long.class))).thenReturn(getClientResponse);

        ResponseEntity<GetClientResponse> res = clientController.getClient(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getClientExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getClientExpectedResponse.getBody());
    }

    @Test
    void getClientList() {
        when(clientService.getClientList()).thenReturn(getClientListResponse);

        ResponseEntity<List<GetClientResponse>> res = clientController.getClientList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getClientListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getClientListExpectedResponse.getBody());
    }

    @Test
    void getClientListByAppId() {
        when(clientService.getClientListByAppId(any(Long.class))).thenReturn(getClientListResponse);

        ResponseEntity<List<GetClientResponse>> res = clientController.getClientListByAppId(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getClientListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getClientListExpectedResponse.getBody());
    }

    @Test
    void updateClient() {
        when(clientService.updateClient(any(Long.class), any(UpdateClientRequest.class))).thenReturn(updateClientResponse);

        ResponseEntity<UpdateClientResponse> res = clientController.updateClient(correlationId, 1L, updateClientRequest);

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
