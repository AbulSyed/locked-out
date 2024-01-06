package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientPageResponse;
import com.syed.identityservice.domain.model.response.ClientResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.ClientService;
import com.syed.identityservice.utility.MapperUtil;
import com.syed.identityservice.utility.Utility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AppRepository appRepository;

    private static final String CLIENT_WITH_ID = "Client with id ";

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
    public ClientResponse getClient(Long id, String appName, String clientId) {
        ClientEntity client = null;

        if (id != null) {
            client = clientRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(CLIENT_WITH_ID + id)));
        } else if (appName != null && clientId != null) {
            AppEntity app = appRepository.findByName(appName);

            if (app == null) {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with name " + appName));
            } else {
                client = clientRepository.getClientEntityByUserAppAndClientId(app, clientId);

                if (client == null) {
                    throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client " + clientId + " with app " + appName));
                }
            }
        }

        return MapperUtil.mapClientEntityToClientResponse(client);
    }

    @Override
    public ClientPageResponse getClientList(int page, int size) {
        Pageable pageable = Utility.createPageable(page, size, Sort.by(Sort.DEFAULT_DIRECTION, "clientId"));

        Page<ClientEntity> clientEntityPage = clientRepository.findAll(pageable);
        List<ClientEntity> clientEntityList = clientEntityPage.getContent();

        List<ClientResponse> clientResponseList = MapperUtil.mapClientEntityListToGetClientListResponse(clientEntityList);

        return MapperUtil.mapClientResponseListToClientPageResponse(
                clientResponseList,
                clientEntityPage.getNumber() + 1,
                clientEntityPage.getSize(),
                clientEntityPage.getTotalElements(),
                clientEntityPage.getTotalPages(),
                clientEntityPage.isLast()
        );
    }

    @Override
    public ClientPageResponse getClientListByApp(Long appId, String appName, int page, int size) {
        Pageable pageable = Utility.createPageable(page, size, Sort.by(Sort.DEFAULT_DIRECTION, "clientId"));

        AppEntity app = null;

        if (appId != null) {
            app = appRepository.findById(appId).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));
        } else if (appName != null) {
            app = appRepository.findByName(appName);
            if (app == null) {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with name " + appName));
            }
        }

        Page<ClientEntity> clientEntityPage = clientRepository.getClientEntitiesByUserApp(app, pageable);
        List<ClientEntity> clientEntityList = clientEntityPage.getContent();

        List<ClientResponse> clientResponseList = MapperUtil.mapClientEntityListToGetClientListResponse(clientEntityList);

        return MapperUtil.mapClientResponseListToClientPageResponse(
                clientResponseList,
                clientEntityPage.getNumber() + 1,
                clientEntityPage.getSize(),
                clientEntityPage.getTotalElements(),
                clientEntityPage.getTotalPages(),
                clientEntityPage.isLast()
        );
    }

    @Override
    public ClientResponse updateClient(Long clientId, ClientRequest request) {
        ClientEntity clientEntity = clientRepository.findById(clientId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(CLIENT_WITH_ID + clientId))
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
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(CLIENT_WITH_ID + clientId))
        );

        clientRepository.delete(clientEntity);
    }
}
