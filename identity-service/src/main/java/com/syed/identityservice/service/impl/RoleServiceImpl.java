package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.ClientEntity;
import com.syed.identityservice.data.entity.RoleEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.data.repository.RoleRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.AddRoleToEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.AddRoleResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.exception.custom.RoleAlreadyPresentException;
import com.syed.identityservice.service.RoleService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        RoleEntity role = roleRepository.save(MapperUtil.mapRoleModelToEntity(request));

        return MapperUtil.mapRoleEntityToResponse(role);
    }

    @Override
    public AddRoleResponse addRole(AddRoleToEnum addRoleTo, Long id, Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Role with id " + id)));

        if (addRoleTo.toString().equals("USER")) {
            System.out.println("we need to a role to a user with id " + id);

            UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + id)));

            if (userEntity.getRoles().contains(roleEntity)) {
                throw new RoleAlreadyPresentException(ErrorConstant.ROLE_ALREADY_PRESENT.formatMessage("user " + userEntity.getUsername()));
            } else {
                userEntity.getRoles().add(roleEntity);
                userRepository.save(userEntity);
            }

            return AddRoleResponse.builder()
                    .message("Role " + roleEntity.getName() + " added to user " + userEntity.getUsername())
                    .build();
        } else if (addRoleTo.toString().equals("CLIENT")) {
            System.out.println("we need to a role to a client with id " + id);

            ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Client with id " + id)));

            if (clientEntity.getRoles().contains(roleEntity)) {
                throw new RoleAlreadyPresentException(ErrorConstant.ROLE_ALREADY_PRESENT.formatMessage("client " + clientEntity.getClientId()));
            } else {
                clientEntity.getRoles().add(roleEntity);
                clientRepository.save(clientEntity);
            }

            return AddRoleResponse.builder()
                    .message("Role " + roleEntity.getName() + " added to client " + clientEntity.getClientId())
                    .build();
        }
        // no need for else clause as DefaultHandlerExceptionResolver exception
        // will be thrown by controller before we enter service layer
        return null;
    }
}
