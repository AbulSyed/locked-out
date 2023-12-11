package com.syed.identityservice;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.AuthorityModel;
import com.syed.identityservice.domain.model.RoleModel;
import com.syed.identityservice.domain.model.response.ClientResponse;

import java.time.LocalDateTime;
import java.util.Set;

public class BaseTest {

    protected ClientResponse createClientResponse(Long id, String clientId, String secret, Set<AuthMethodEnum> authMethods,
                                                  Set<AuthGrantTypeEnum> authGrantTypes, Set<AuthorityModel> authorities, Set<RoleModel> roles,
                                                  Set<ScopeEnum> scopes, String redirectUri, LocalDateTime createdAt) {
        return ClientResponse.builder()
                .id(id)
                .clientId(clientId)
                .clientSecret(secret)
                .authMethod(authMethods)
                .authGrantType(authGrantTypes)
                .authorities(authorities)
                .roles(roles)
                .scopes(scopes)
                .redirectUri(redirectUri)
                .createdAt(createdAt)
                .build();
    }
}
