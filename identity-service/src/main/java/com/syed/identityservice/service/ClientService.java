package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.domain.model.response.GetClientResponse;

public interface ClientService {

    CreateClientResponse createClient(Long appId, CreateClientRequest request);
    GetClientResponse getClient(Long clientId);
}
