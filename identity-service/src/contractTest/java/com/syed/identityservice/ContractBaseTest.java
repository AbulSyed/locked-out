package com.syed.identityservice;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.AuthorityModel;
import com.syed.identityservice.domain.model.RoleModel;
import com.syed.identityservice.domain.model.response.ClientResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.Set;

public class ContractBaseTest {

    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:14.2-alpine");
        POSTGRE_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    }

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
