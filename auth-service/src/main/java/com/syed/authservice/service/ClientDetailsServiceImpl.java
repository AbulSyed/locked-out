package com.syed.authservice.service;

import com.syed.authservice.domain.model.enums.AuthGrantTypeEnum;
import com.syed.authservice.domain.model.enums.ScopeEnum;
import com.syed.authservice.filter.ClientContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

// In the new Spring Authorization Server, the ClientDetailsService interface has been replaced by the RegisteredClientRepository interface.
// The RegisteredClientRepository interface provides a similar functionality of retrieving client details by client ID as the ClientDetailsService interface.
@Service
public class ClientDetailsServiceImpl implements RegisteredClientRepository {

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Set<String> scopeSet = ClientContextHolder.getClientResponse().getScopes().stream()
                .map(ScopeEnum::getValue)
                .collect(Collectors.toSet());
        Set<AuthorizationGrantType> grantTypesList = ClientContextHolder.getClientResponse().getAuthGrantType().stream()
                .map(AuthGrantTypeEnum::getValue)
                .map(AuthorizationGrantType::new)
                .collect(Collectors.toSet());

        return RegisteredClient.withId(clientId)
                .clientId(ClientContextHolder.getClientResponse().getClientId())
                .clientSecret(ClientContextHolder.getClientResponse().getClientSecret())
                .redirectUri(ClientContextHolder.getClientResponse().getRedirectUri())
                .authorizationGrantTypes(grantTypes -> grantTypes.addAll(grantTypesList))
                .scopes(scopes -> scopes.addAll(scopeSet))
                .clientSettings(
                        ClientSettings.builder()
                                .requireProofKey(false)
                                .requireAuthorizationConsent(false)
                                .build()
                )
                .tokenSettings(
                        TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofHours(1))
                                .refreshTokenTimeToLive(Duration.ofDays(7))
                                .build()
                )
                .build();
    }
}
