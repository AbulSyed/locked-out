package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.entity.RoleEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.data.repository.RoleRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.exception.custom.RoleAlreadyPresentException;
import com.syed.identityservice.service.RoleService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    private static final String ROLE_WITH_ID = "Role with id ";

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        RoleEntity role = roleRepository.save(MapperUtil.mapRoleModelToEntity(request));

        return MapperUtil.mapRoleEntityToResponse(role);
    }

    @Override
    public MessageResponse addRole(RoleToEnum addRoleTo, Long id, Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(ROLE_WITH_ID + roleId)));

        if (addRoleTo.toString().equals("USER")) {
            log.info("we need to add a role to a user with id {}", id);

            UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + id)));

            if (userEntity.getRoles().contains(roleEntity)) {
                throw new RoleAlreadyPresentException(ErrorConstant.ROLE_ALREADY_PRESENT.formatMessage("user " + userEntity.getUsername()));
            } else {
                userEntity.getRoles().add(roleEntity);
                userRepository.save(userEntity);
            }

            return MessageResponse.builder()
                    .message("Role " + roleEntity.getName() + " added to user " + userEntity.getUsername())
                    .build();
        } else if (addRoleTo.toString().equals("CLIENT")) {
            log.info("we need to add a role to a client with id {}", id);

            ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + id)));

            if (clientEntity.getRoles().contains(roleEntity)) {
                throw new RoleAlreadyPresentException(ErrorConstant.ROLE_ALREADY_PRESENT.formatMessage("client " + clientEntity.getClientId()));
            } else {
                clientEntity.getRoles().add(roleEntity);
                clientRepository.save(clientEntity);
            }

            return MessageResponse.builder()
                    .message("Role " + roleEntity.getName() + " added to client " + clientEntity.getClientId())
                    .build();
        }
        // no need for else clause as DefaultHandlerExceptionResolver exception
        // will be thrown by controller before we enter service layer
        return null;
    }

    @Override
    public List<RoleResponse> getRoleList() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();

        return MapperUtil.mapRoleEntityListToRoleResponseList(roleEntityList);
    }

    @Override
    public void deleteRoleFrom(RoleToEnum deleteRoleFrom, Long id, Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(ROLE_WITH_ID + roleId)));

        if (deleteRoleFrom.toString().equals("USER")) {
            log.info("we need to delete a role from a user with id {}", id);

            UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + id)));

            if (userEntity.getRoles().contains(roleEntity)) {
                userEntity.getRoles().remove(roleEntity);
                userRepository.save(userEntity);
            } else {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Role with " + id + " not found with user"));
            }
        } else if (deleteRoleFrom.toString().equals("CLIENT")) {
            log.info("we need to delete a role from a client with id {}", id);

            ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + id)));

            if (clientEntity.getRoles().contains(roleEntity)) {
                clientEntity.getRoles().remove(roleEntity);
                clientRepository.save(clientEntity);
            } else {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Role with " + id + " not found with client"));
            }
        }
    }

    @Override
    public void deleteRole(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(ROLE_WITH_ID + roleId)));

        // remove the associations from users
        for (UserEntity user : roleEntity.getUsers()) {
            user.getRoles().remove(roleEntity);
        }
        // clear the users associated with the role
        roleEntity.getUsers().clear();

        // remove the associations from clients
        for (ClientEntity client : roleEntity.getClients()) {
            client.getRoles().remove(roleEntity);
        }
        // clear the clients associated with the role
        roleEntity.getClients().clear();

        roleRepository.delete(roleEntity);
    }
}
