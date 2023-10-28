package com.syed.identityservice.service;

import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.AddRoleResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    AddRoleResponse addRole(RoleToEnum addRoleTo, Long id, Long roleId);
    void deleteRoleFrom(RoleToEnum deleteRoleFrom, Long id, Long roleId);
}
