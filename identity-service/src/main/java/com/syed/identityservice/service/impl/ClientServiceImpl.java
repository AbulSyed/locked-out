package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.ClientService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
