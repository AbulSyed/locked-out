package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;

public interface AuthorityService {

    AuthorityResponse createAuthority(AuthorityRequest request);
}
