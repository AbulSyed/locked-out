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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public MessageResponse addAuthority(AuthorityToEnum addAuthorityTo, Long id, Long authorityId) {
        AuthorityEntity authorityEntity = authorityRepository.findById(authorityId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(AUTHORITY_WITH_ID + authorityId)));

        if (addAuthorityTo.toString().equals("USER")) {
            log.info("we need to add a authority to a user with id {}", id);

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
            log.info("we need to add a authority to a client with id {}", id);

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

    @Override
    public List<AuthorityResponse> getAuthorityList() {
        List<AuthorityEntity> authorityEntityList = authorityRepository.findAll();

        return MapperUtil.mapAuthorityEntityAuthorityResponseList(authorityEntityList);
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
