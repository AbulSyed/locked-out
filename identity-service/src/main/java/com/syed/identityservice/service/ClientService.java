package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientPageResponse;
import com.syed.identityservice.domain.model.response.ClientResponse;

import java.util.List;

public interface ClientService {

    ClientResponse createClient(Long appId, ClientRequest request);
    ClientResponse getClient(Long id, String appName, String clientId);
    ClientPageResponse getClientList(int page, int size);
    List<ClientResponse> getClientListByApp(Long appId, String appName);
    ClientResponse updateClient(Long clientId, ClientRequest request);
    void deleteClient(Long clientId);
}
