package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AuthorityEntity;
import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AuthorityRepository;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.AuthorityToEnum;
import com.syed.identityservice.domain.model.request.AlterAuthorityRequest;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.AuthorityAlreadyPresentException;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.InvalidRequestException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.AuthorityService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    private static final String AUTHORITY_WITH_ID = "Authority with id ";

    @Override
    public AuthorityResponse createAuthority(AuthorityRequest request) {
        if (authorityRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        AuthorityEntity authority = authorityRepository.save(MapperUtil.mapAuthorityModelToEntity(request));

        return MapperUtil.mapAuthorityEntityToResponse(authority);
    }

    @Override
    public MessageResponse alterAuthority(AuthorityToEnum addAuthorityTo, AlterAuthorityRequest alterAuthorityRequest) {
        Set<AuthorityEntity> authorityEntitySet = authorityRepository.findByIdIn(alterAuthorityRequest.getAuthorityIds());

        if (authorityEntitySet.isEmpty()) {
            return MessageResponse.builder()
                    .message("No authorities found to add")
                    .build();
        }

        if (addAuthorityTo.toString().equals("USER")) {
            if (alterAuthorityRequest.getUserId() == null) {
                throw new InvalidRequestException("User id must be present");
            }

            log.info("we need to add authority/s to user with id {}", alterAuthorityRequest.getUserId());

            UserEntity userEntity = userRepository.findById(alterAuthorityRequest.getUserId()).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + alterAuthorityRequest.getUserId())));

            userEntity.getAuthorities().clear();
            userEntity.setAuthorities(authorityEntitySet);
            userRepository.save(userEntity);

            return MessageResponse.builder()
                    .message("Authority/s added to user " + userEntity.getUsername())
                    .build();
        } else if (addAuthorityTo.toString().equals("CLIENT")) {
            if (alterAuthorityRequest.getClientId() == null) {
                throw new InvalidRequestException("Client id must be present");
            }

            log.info("we need to add authority/s to client with id {}", alterAuthorityRequest.getClientId());

            ClientEntity clientEntity = clientRepository.findById(alterAuthorityRequest.getClientId()).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + alterAuthorityRequest.getClientId())));

            clientEntity.getAuthorities().clear();
            clientEntity.setAuthorities(authorityEntitySet);
            clientRepository.save(clientEntity);

            return MessageResponse.builder()
                    .message("Authority/s added to client " + clientEntity.getClientId())
                    .build();
        }
        // no need for else clause as DefaultHandlerExceptionResolver exception
        // will be thrown by controller before we enter service layer
        return null;
    }

    @Override
    public List<String> getAuthorityList() {
        List<AuthorityEntity> authorityEntityList = authorityRepository.findAll();

        return MapperUtil.mapAuthorityEntityListToStringList(authorityEntityList);
    }

    @Override
    public void deleteAuthorityFrom(AuthorityToEnum deleteAuthorityFrom, Long id, Long authorityId) {
        AuthorityEntity authorityEntity = authorityRepository.findById(authorityId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(AUTHORITY_WITH_ID + authorityId)));

        if (deleteAuthorityFrom.toString().equals("USER")) {
            log.info("we need to delete a authority from a user with id {}", id);

            UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + id)));

            if (userEntity.getAuthorities().contains(authorityEntity)) {
                userEntity.getAuthorities().remove(authorityEntity);
                userRepository.save(userEntity);
            } else {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Authority with " + id + " not found with user"));
            }
        } else if (deleteAuthorityFrom.toString().equals("CLIENT")) {
            log.info("we need to delete a authority from a client with id {}", id);

            ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + id)));

            if (clientEntity.getAuthorities().contains(authorityEntity)) {
                clientEntity.getAuthorities().remove(authorityEntity);
                clientRepository.save(clientEntity);
            } else {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Authority with " + id + " not found with client"));
            }
        }
    }

    @Override
    public void deleteAuthority(Long authorityId) {
        AuthorityEntity authorityEntity = authorityRepository.findById(authorityId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(AUTHORITY_WITH_ID + authorityId)));

        // remove the associations from users
        for (UserEntity user : authorityEntity.getUsers()) {
            user.getAuthorities().remove(authorityEntity);
        }
        // clear the users associated with the authority
        authorityEntity.getUsers().clear();

        // remove the associations from clients
        for (ClientEntity client : authorityEntity.getClients()) {
            client.getAuthorities().remove(authorityEntity);
        }
        // clear the clients associated with the authority
        authorityEntity.getClients().clear();

        authorityRepository.delete(authorityEntity);
    }
}
