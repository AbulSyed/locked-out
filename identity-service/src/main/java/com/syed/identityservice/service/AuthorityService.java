package com.syed.identityservice.service;

import com.syed.identityservice.domain.enums.AuthorityToEnum;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AddAuthorityResponse;
import com.syed.identityservice.domain.model.response.AuthorityResponse;

public interface AuthorityService {

    AuthorityResponse createAuthority(AuthorityRequest request);
    AddAuthorityResponse addAuthority(AuthorityToEnum addAuthorityTo, Long id, Long authorityId);
}
