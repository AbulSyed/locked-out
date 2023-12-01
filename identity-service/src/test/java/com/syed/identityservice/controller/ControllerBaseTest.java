package com.syed.identityservice.controller;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.request.*;
import com.syed.identityservice.domain.model.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Set;

public class ControllerBaseTest<T> {

    protected String correlationId = "1";

    protected AppRequest createAppRequest(String name, String description) {
        return AppRequest.builder()
                .name(name)
                .description(description)
                .build();
    }

    protected AppResponse createAppResponse(Long id, String name, String description, LocalDateTime createdAt) {
        return AppResponse.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    protected ResponseEntity<T> createExpectedResponse(HttpStatus status, T body) {
        return ResponseEntity.status(status).body(body);
    }

    protected AppV2Response createAppV2Response(Long id, String name, String description, LocalDateTime createdAt) {
        return AppV2Response.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    protected UserRequest createUserRequest(String username, String password, String email, String phoneNumber) {
        return UserRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }

    protected UserResponse createUserResponse(Long id, String username, String password, String email, String phoneNumber, LocalDateTime createdAt) {
        return UserResponse.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .createdAt(createdAt)
                .build();
    }

    protected UserV2Response createUserV2Response(Long id, String username, String password, String email, String phoneNumber, LocalDateTime createdAt) {
        return UserV2Response.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .createdAt(createdAt)
                .build();
    }

    protected AuthorityRequest createAuthorityRequest(String name) {
        return AuthorityRequest.builder()
                .name(name)
                .build();
    }

    protected AuthorityResponse createAuthorityResponse(Long id, String name) {
        return AuthorityResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    protected MessageResponse createMessageResponse(String message) {
        return MessageResponse.builder()
                .message(message)
                .build();
    }

    protected ClientRequest createClientRequest(String clientId, String secret, Set<AuthMethodEnum> authMethods, Set<AuthGrantTypeEnum> authGrantTypes, String redirectUri) {
        return ClientRequest.builder()
                .clientId(clientId)
                .clientSecret(secret)
                .authMethod(authMethods)
                .authGrantType(authGrantTypes)
                .redirectUri(redirectUri)
                .build();
    }

    protected ClientResponse createClientResponse(Long id, String clientId, String secret, Set<AuthMethodEnum> authMethods, Set<AuthGrantTypeEnum> authGrantTypes, String redirectUri, LocalDateTime createdAt) {
        return ClientResponse.builder()
                .id(id)
                .clientId(clientId)
                .clientSecret(secret)
                .authMethod(authMethods)
                .authGrantType(authGrantTypes)
                .redirectUri(redirectUri)
                .createdAt(createdAt)
                .build();
    }

    protected RoleRequest createRoleRequest(String name) {
        return RoleRequest.builder()
                .name(name)
                .build();
    }

    protected RoleResponse createRoleResponse(Long id, String name) {
        return RoleResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    protected ScopeRequest createScopeRequest(Set<ScopeEnum> scopes) {
        return ScopeRequest.builder()
                .scopes(scopes)
                .build();
    }
}
