package com.syed.identityservice.controller;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.domain.model.response.GetClientResponse;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
}
