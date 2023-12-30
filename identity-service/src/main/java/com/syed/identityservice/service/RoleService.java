package com.syed.identityservice.service;

import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.AlterRoleRequest;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    MessageResponse alterRoles(RoleToEnum addRoleTo, AlterRoleRequest alterRoleRequest);
    List<RoleResponse> getRoleList();
    void deleteRoleFrom(RoleToEnum deleteRoleFrom, Long id, Long roleId);
    void deleteRole(Long roleId);
}
