package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.*;
import com.syed.identityservice.domain.model.request.AppRequest;

import java.time.LocalDateTime;
import java.util.Set;

public class ServiceImplBaseTest {

    protected AppRequest createAppRequest(String name, String description) {
        return AppRequest.builder()
                .name(name)
                .description(description)
                .build();
    }

    protected AppEntity createAppEntity(Long id, String name, String description, LocalDateTime createdAt) {
        return AppEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    protected AppEntity createAppEntity(Long id, String name, String description, Set<UserEntity> users, Set<ClientEntity> clients,
                                        LocalDateTime createdAt) {
        return AppEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .users(users)
                .clients(clients)
                .createdAt(createdAt)
                .build();
    }

    protected UserEntity createUserEntity(Long id, String username, String password, String email, String phoneNumber,
                                          Set<RoleEntity> roles, Set<AuthorityEntity> authorities, LocalDateTime createdAt) {
        return UserEntity.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .authorities(authorities)
                .createdAt(createdAt)
                .build();
    }

    protected ClientEntity createClientEntity(Long id, String clientId, String secret, Set<RoleEntity> roles, Set<AuthorityEntity> authorities,
                                              LocalDateTime createdAt) {
        return ClientEntity.builder()
                .id(id)
                .clientId(clientId)
                .secret(secret)
                .roles(roles)
                .authorities(authorities)
                .createdAt(createdAt)
                .build();
    }
}
