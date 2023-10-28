package com.syed.identityservice.service;

import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.AddRoleResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    AddRoleResponse addRole(RoleToEnum addRoleTo, Long id, Long roleId);
    List<String> getRoleList();
    void deleteRoleFrom(RoleToEnum deleteRoleFrom, Long id, Long roleId);
    void deleteRole(Long roleId);
}
