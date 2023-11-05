package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.ScopeRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;

public interface ScopeService {

    MessageResponse alterClientScopes(Long id, ScopeRequest request);
}
