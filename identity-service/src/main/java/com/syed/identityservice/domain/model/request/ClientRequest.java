package com.syed.identityservice.domain.model.request;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClientRequest {

    private String clientId;
    private String clientSecret;
    private Set<AuthMethodEnum> authMethod;
    private Set<AuthGrantTypeEnum> authGrantType;
    private String redirectUri;
}
