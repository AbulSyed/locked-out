package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientPageResponse;
import com.syed.identityservice.domain.model.response.ClientResponse;

public interface ClientService {

    ClientResponse createClient(Long appId, ClientRequest request);
    ClientResponse getClient(Long id, String appName, String clientId);
    ClientPageResponse getClientList(int size, String cursor);
    ClientPageResponse getClientListByApp(Long appId, String appName, int size, String cursor);
    ClientResponse updateClient(Long clientId, ClientRequest request);
    void deleteClient(Long clientId);
}
