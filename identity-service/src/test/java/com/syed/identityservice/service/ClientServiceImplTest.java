package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.domain.model.response.GetClientResponse;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.impl.ClientServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private AppEntity appEntity;
    private ClientEntity clientEntity;
    private CreateClientRequest createClientRequest;

    @BeforeEach
    void setUp() {
        appEntity = AppEntity.builder()
                .id(1L)
                .name("app")
                .description("desc")
                .createdAt(LocalDateTime.now())
                .build();

        clientEntity = ClientEntity.builder()
                .id(1L)
                .clientId("1")
                .secret("secret")
                .roles(Collections.emptySet())
                .authorities(Collections.emptySet())
                .scope(Collections.emptySet())
                .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                .redirectUri("http://localhost:3000")
                .createdAt(LocalDateTime.now())
                .build();
        createClientRequest = CreateClientRequest.builder()
                .clientId("1")
                .clientSecret("secret")
                .authMethod(Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .authGrantType(Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE))
                .redirectUri("http://localhost:3000")
                .build();
    }

    @Test
    void createClient() {
        when(clientRepository.existsByClientId(any(String.class))).thenReturn(false);
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        CreateClientResponse res = clientService.createClient(1L, createClientRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("clientId", "1")
                .hasFieldOrPropertyWithValue("clientSecret", "secret")
                .hasFieldOrPropertyWithValue("authMethod", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .hasFieldOrPropertyWithValue("authGrantType", Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE));
    }

    @Test
    void getClient() {
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(clientEntity));

        GetClientResponse res = clientService.getClient(1L);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("clientId", "1")
                .hasFieldOrPropertyWithValue("clientSecret", "secret")
                .hasFieldOrPropertyWithValue("authMethod", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .hasFieldOrPropertyWithValue("authGrantType", Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE));
    }

    @Test
    void getClient_ThrowsResourceNotFoundException() {
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> clientService.getClient(1L));

        assertEquals("Client with id 1 not found", throwable.getMessage());
    }
}
