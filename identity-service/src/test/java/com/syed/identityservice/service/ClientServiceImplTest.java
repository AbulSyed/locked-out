package com.syed.identityservice.service;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientResponse;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest extends BaseTest<Object> {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AppRepository appRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void createClient() {
        AppEntity appEntity = createAppEntity(1L,"app","desc",LocalDateTime.now());
        ClientEntity clientEntity = createClientEntity(1L,"1","secret", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE),
                "http://localhost:3000", LocalDateTime.now());
        ClientRequest createClientRequest = createClientRequest("1", "secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000");

        when(clientRepository.existsByClientId(any(String.class))).thenReturn(false);
        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        ClientResponse res = clientService.createClient(1L, createClientRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("clientId", "1")
                .hasFieldOrPropertyWithValue("clientSecret", "secret")
                .hasFieldOrPropertyWithValue("authMethod", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .hasFieldOrPropertyWithValue("authGrantType", Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE));
    }

    @Test
    void getClient() {
        ClientEntity clientEntity = createClientEntity(1L,"1","secret", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE),
                "http://localhost:3000", LocalDateTime.now());

        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(clientEntity));

        ClientResponse res = clientService.getClient(1L, null, null);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("clientId", "1")
                .hasFieldOrPropertyWithValue("clientSecret", "secret")
                .hasFieldOrPropertyWithValue("authMethod", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC))
                .hasFieldOrPropertyWithValue("authGrantType", Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE));
    }

    @Test
    void getClient_ThrowsResourceNotFoundException() {
        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> clientService.getClient(1L, null, null));

        assertEquals("Client with id 1 not found", throwable.getMessage());
    }

    @Test
    void getClientList() {
        ClientEntity clientEntity = createClientEntity(1L,"1","secret", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE),
                "http://localhost:3000", LocalDateTime.now());
        List<ClientEntity> clientEntityList = List.of(clientEntity);

        when(clientRepository.findAll()).thenReturn(clientEntityList);

        List<ClientResponse> res = clientService.getClientList();

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void getClientListByApp() {
        AppEntity appEntity = createAppEntity(1L,"app","desc",LocalDateTime.now());
        ClientEntity clientEntity = createClientEntity(1L,"1","secret", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE),
                "http://localhost:3000", LocalDateTime.now());
        List<ClientEntity> clientEntityList = List.of(clientEntity);

        when(appRepository.findById(any(Long.class))).thenReturn(Optional.of(appEntity));
        when(clientRepository.getClientEntitiesByUserApp(any(AppEntity.class))).thenReturn(clientEntityList);

        List<ClientResponse> res = clientService.getClientListByApp(1L, null);

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void updateClient() {
        ClientEntity clientEntity = createClientEntity(1L,"1","secret", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE),
                "http://localhost:3000", LocalDateTime.now());
        ClientRequest updateClientRequest = createClientRequest("new client id", "secret", Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC),
                Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE), "http://localhost:3000");
        ClientEntity updatedClientEntity = createClientEntity(1L, "new client id", "secret", Collections.emptySet(),
                Collections.emptySet(), LocalDateTime.now());

        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(clientEntity));
        when(clientRepository.existsByClientId("new client id")).thenReturn(false);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(updatedClientEntity);

        ClientResponse res = clientService.updateClient(1L, updateClientRequest);

        assertThat(res)
                .isNotNull()
                .hasFieldOrPropertyWithValue("clientId", "new client id");
    }

    @Test
    void deleteClient() {
        ClientEntity clientEntity = createClientEntity(1L,"1","secret", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE),
                "http://localhost:3000", LocalDateTime.now());

        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(clientEntity));

        clientService.deleteClient(1L);

        verify(clientRepository).delete(clientEntity);
    }
}
