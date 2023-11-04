package com.syed.identityservice.domain.model.request;

import com.syed.identityservice.domain.enums.ScopeEnum;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScopeRequest {

    private Set<ScopeEnum> scopes;
}
