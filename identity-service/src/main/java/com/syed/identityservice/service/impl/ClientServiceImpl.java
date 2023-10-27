package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.ClientService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AppRepository appRepository;

    @Override
    public ClientResponse createClient(Long appId, ClientRequest request) {
        if (clientRepository.existsByClientId(request.getClientId())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Client Id"));
        }

        AppEntity app = appRepository.findById(appId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));

        ClientEntity client = MapperUtil.mapClientModelToEntity(request);
        client.setUserApp(app);

        return MapperUtil.mapClientEntityToClientResponse(clientRepository.save(client));
    }

    @Override
    public ClientResponse getClient(Long clientId) {
        ClientEntity client = clientRepository.findById(clientId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + clientId)));

        return MapperUtil.mapClientEntityToClientResponse(client);
    }

    @Override
    public List<ClientResponse> getClientList() {
        List<ClientEntity> clients = clientRepository.findAll();

        return MapperUtil.mapClientEntityListToGetClientListResponse(clients);
    }

    @Override
    public List<ClientResponse> getClientListByAppId(Long appId) {
        AppEntity app = appRepository.findById(appId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));

        List<ClientEntity> clients = clientRepository.getClientEntitiesByUserApp(app);

        return MapperUtil.mapClientEntityListToGetClientListResponse(clients);
    }

    @Override
    public ClientResponse updateClient(Long clientId, ClientRequest request) {
        ClientEntity clientEntity = clientRepository.findById(clientId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + clientId))
        );

        if (!request.getClientId().equals(clientEntity.getClientId()) && clientRepository.existsByClientId(request.getClientId())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Client id"));
        }

        clientEntity.setClientId(request.getClientId());
        clientEntity.setSecret(request.getClientSecret());
        clientEntity.setAuthMethod(request.getAuthMethod());
        clientEntity.setAuthGrantType(request.getAuthGrantType());
        clientEntity.setRedirectUri(request.getRedirectUri());

        clientRepository.save(clientEntity);

        return MapperUtil.mapClientEntityToClientResponse(clientEntity);
    }

    @Override
    public void deleteClient(Long clientId) {
        ClientEntity clientEntity = clientRepository.findById(clientId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + clientId))
        );

        clientRepository.delete(clientEntity);
    }
}
