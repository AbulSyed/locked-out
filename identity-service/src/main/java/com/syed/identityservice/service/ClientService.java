package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientResponse;

import java.util.List;

public interface ClientService {

    ClientResponse createClient(Long appId, ClientRequest request);
    ClientResponse getClient(Long clientId);
    List<ClientResponse> getClientList();
    List<ClientResponse> getClientListByApp(Long appId, String appName);
    ClientResponse updateClient(Long clientId, ClientRequest request);
    void deleteClient(Long clientId);
}
