package com.syed.identityservice.domain.model;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.enums.ScopeEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClientModel {

    private Long id;
    private String clientId;
    private String secret;
    private Set<RoleModel> roles;
    private Set<AuthorityModel> authorities;
    private Set<ScopeEnum> scopes;
    private Set<AuthMethodEnum> authMethods;
    private Set<AuthGrantTypeEnum> authGrantTypes;
    private String redirectUri;
    private LocalDateTime createdAt;
}
