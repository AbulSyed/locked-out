package com.syed.identityservice.domain.model.response;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.AuthorityModel;
import com.syed.identityservice.domain.model.RoleModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClientResponse {

    private Long id;
    private String clientId;
    private String clientSecret;
    private Set<RoleModel> roles;
    private Set<AuthorityModel> authorities;
    private Set<ScopeEnum> scopes;
    private Set<AuthMethodEnum> authMethod;
    private Set<AuthGrantTypeEnum> authGrantType;
    private String redirectUri;
    private LocalDateTime createdAt;
}
