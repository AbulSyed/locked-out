package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.RoleEntity;
import com.syed.identityservice.data.repository.RoleRepository;
import com.syed.identityservice.domain.model.request.CreateRoleRequest;
import com.syed.identityservice.domain.model.response.CreateRoleResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.service.RoleService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public CreateRoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        RoleEntity role = roleRepository.save(MapperUtil.mapRoleModelToEntity(request));

        return MapperUtil.mapRoleEntityToModel(role);
    }
}
