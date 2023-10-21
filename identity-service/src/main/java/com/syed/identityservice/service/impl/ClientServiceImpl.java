package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.request.UpdateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.domain.model.response.GetClientResponse;
import com.syed.identityservice.domain.model.response.UpdateClientResponse;
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
    public CreateClientResponse createClient(Long appId, CreateClientRequest request) {
        if (clientRepository.existsByClientId(request.getClientId())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Client Id"));
        }

        AppEntity app = appRepository.findById(appId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));

        ClientEntity client = MapperUtil.mapClientModelToEntity(request);
        client.setUserApp(app);

        return MapperUtil.mapClientEntityToCreateClientResponse(clientRepository.save(client));
    }

    @Override
    public GetClientResponse getClient(Long clientId) {
        ClientEntity client = clientRepository.findById(clientId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + clientId)));

        return MapperUtil.mapClientEntityToGetClientResponse(client);
    }

    @Override
    public List<GetClientResponse> getClientList() {
        List<ClientEntity> clients = clientRepository.findAll();

        return MapperUtil.mapClientEntityListToGetClientListResponse(clients);
    }

    @Override
    public UpdateClientResponse updateClient(Long clientId, UpdateClientRequest request) {
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

        return MapperUtil.clientEntityToUpdateClientResponse(clientEntity);
    }

    @Override
    public void deleteClient(Long clientId) {
        ClientEntity clientEntity = clientRepository.findById(clientId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + clientId))
        );

        clientRepository.delete(clientEntity);
    }
}
