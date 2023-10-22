package com.syed.identityservice.domain.model.response;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetClientResponse {

    private Long id;
    private String clientId;
    private String clientSecret;
    private Set<AuthMethodEnum> authMethod;
    private Set<AuthGrantTypeEnum> authGrantType;
    private String redirectUri;
    private LocalDateTime createdAt;
}
