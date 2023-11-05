package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.domain.enums.ScopeEnum;
import com.syed.identityservice.domain.model.request.ScopeRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.ScopeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ScopeServiceImpl implements ScopeService {

    private final ClientRepository clientRepository;

    @Override
    public MessageResponse alterClientScopes(Long id, ScopeRequest request) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + id)));

        client.setScope(request.getScopes());
        clientRepository.save(client);

        return MessageResponse.builder()
                .message("Client " + id + " updated with scopes " + client.getScope().stream()
                        .map(ScopeEnum::getValue)
                        .toList())
                .build();
    }
}
