package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.RoleEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.ClientRepository;
import com.syed.identityservice.data.repository.RoleRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.AddRoleToEnum;
import com.syed.identityservice.domain.model.RoleModel;
import com.syed.identityservice.domain.model.request.CreateRoleRequest;
import com.syed.identityservice.domain.model.response.AddRoleResponse;
import com.syed.identityservice.domain.model.response.CreateRoleResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.RoleService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Override
    public CreateRoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        RoleEntity role = roleRepository.save(MapperUtil.mapRoleModelToEntity(request));

        return MapperUtil.mapRoleEntityToModel(role);
    }

    @Override
    public AddRoleResponse addRole(AddRoleToEnum addRoleTo, Long id, Long roleId) {
//        if (addRoleTo.toString().equals("USER")) {
//            System.out.println("we need to a role to a user with id " + id);
//            RoleEntity role = roleRepository.findById(roleId).orElseThrow(() ->
//                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Role with id " + id)));
//
//            UserEntity user = userRepository.findById(id).orElseThrow(() ->
//                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + id)));
//
//            RoleModel roleModel = MapperUtil.mapRoleE
////            addRoleToEntity(userRepository, null, id);
//        } else if (addRoleTo.toString().equals("CLIENT")) {
//            System.out.println("we need to a role to a client with id " + id);
////            addRoleToEntity(null, clientRepository, id);
//        }
//        // no need for else clause as DefaultHandlerExceptionResolver exception
//        // will be thrown by controller before we enter service layer
        return null;
    }

//    private void addRoleToEntity(UserRepository userRepository, ClientRepository clientRepository, Long id) {
//        if (userRepository != null) {
//            System.out.println("userRepository != null");
//            UserEntity user = userRepository.findById(id).orElseThrow(() ->
//                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + id)));
//
//            user.setr
//        } else if (clientRepository != null) {
//            System.out.println("clientRepository != null");
//        }
//    }
}
