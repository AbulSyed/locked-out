package com.syed.identityservice.service;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.request.ScopeRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.service.impl.ScopeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScopeServiceImplTest extends BaseTest<Object> {
    
    @Mock
    private ClientRepository clientRepository;
    
    @InjectMocks
    private ScopeServiceImpl scopeService;

    @Test
    void alterClientScopes() {
        ClientEntity clientEntity = createClientEntity(1L,"1","secret", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Set.of(AuthMethodEnum.CLIENT_SECRET_BASIC), Set.of(AuthGrantTypeEnum.AUTHORIZATION_CODE),
                "http://localhost:3000", LocalDateTime.now());
        ScopeRequest scopeRequest = createScopeRequest(Set.of(ScopeEnum.OPENID));

        when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(clientEntity));

        MessageResponse res = scopeService.alterClientScopes(1L, scopeRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("message", "Client 1 updated with scopes [openid]");
    }
}
