package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.request.UpdateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.domain.model.response.GetClientResponse;
import com.syed.identityservice.domain.model.response.UpdateClientResponse;

import java.util.List;

public interface ClientService {

    CreateClientResponse createClient(Long appId, CreateClientRequest request);
    GetClientResponse getClient(Long clientId);
    List<GetClientResponse> getClientList();
    UpdateClientResponse updateClient(Long clientId, UpdateClientRequest request);
}
