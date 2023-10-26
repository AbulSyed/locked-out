package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateRoleRequest;
import com.syed.identityservice.domain.model.response.CreateRoleResponse;

public interface RoleService {

    CreateRoleResponse createRole(CreateRoleRequest request);
}
