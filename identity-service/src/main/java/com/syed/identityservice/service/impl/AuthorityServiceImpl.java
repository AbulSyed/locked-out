package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AuthorityEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AuthorityRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.AuthorityToEnum;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.AuthorityAlreadyPresentException;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.AuthorityService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Override
    public AuthorityResponse createAuthority(AuthorityRequest request) {
        if (authorityRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        AuthorityEntity authority = authorityRepository.save(MapperUtil.mapAuthorityModelToEntity(request));

        return MapperUtil.mapAuthorityEntityToResponse(authority);
    }

    @Override
    public MessageResponse addAuthority(AuthorityToEnum addAuthorityTo, Long id, Long authorityId) {
        AuthorityEntity authorityEntity = authorityRepository.findById(authorityId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Authority with id " + authorityId)));

        if (addAuthorityTo.toString().equals("USER")) {
            System.out.println("we need to add a authority to a user with id " + id);

            UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + id)));

            if (userEntity.getAuthorities().contains(authorityEntity)) {
                throw new AuthorityAlreadyPresentException(ErrorConstant.AUTHORITY_ALREADY_PRESENT.formatMessage("user " + userEntity.getUsername()));
            } else {
                userEntity.getAuthorities().add(authorityEntity);
                userRepository.save(userEntity);
            }

            return MessageResponse.builder()
                    .message("Authority " + authorityEntity.getName() + " added to user " + userEntity.getUsername())
                    .build();
        } else if (addAuthorityTo.toString().equals("CLIENT")) {
            System.out.println("we need to add a authority to a client with id " + id);

            ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + id)));

            if (clientEntity.getAuthorities().contains(authorityEntity)) {
                throw new AuthorityAlreadyPresentException(ErrorConstant.AUTHORITY_ALREADY_PRESENT.formatMessage("client " + clientEntity.getClientId()));
            } else {
                clientEntity.getAuthorities().add(authorityEntity);
                clientRepository.save(clientEntity);
            }

            return MessageResponse.builder()
                    .message("Authority " + authorityEntity.getName() + " added to client " + clientEntity.getClientId())
                    .build();
        }
        // no need for else clause as DefaultHandlerExceptionResolver exception
        // will be thrown by controller before we enter service layer
        return null;
    }
}
