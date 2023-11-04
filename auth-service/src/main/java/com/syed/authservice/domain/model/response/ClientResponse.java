package com.syed.authservice.domain.model.response;

import com.syed.authservice.domain.model.AuthorityModel;
import com.syed.authservice.domain.model.RoleModel;
import com.syed.authservice.domain.model.enums.AuthGrantTypeEnum;
import com.syed.authservice.domain.model.enums.AuthMethodEnum;
import com.syed.authservice.domain.model.enums.ScopeEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
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
