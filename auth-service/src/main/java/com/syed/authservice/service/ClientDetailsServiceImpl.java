package com.syed.authservice.service;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;

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
        return RegisteredClient.withId(clientId)
                .clientId("client")
                .clientSecret("secret")
                .redirectUri("http://127.0.0.1:3000/authorized")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.EMAIL)
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
