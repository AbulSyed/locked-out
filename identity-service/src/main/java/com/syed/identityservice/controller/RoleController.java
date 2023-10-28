package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.AddRoleToEnum;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.AddRoleResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;
import com.syed.identityservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RoleController {

    private final RoleService roleService;

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.ROLE,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "create role request initiated"
    )
    @PostMapping("/create-role")
    public ResponseEntity<RoleResponse> createRole(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @Valid @RequestBody RoleRequest request) {
        return new ResponseEntity<>(roleService.createRole(request), HttpStatus.CREATED);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.ROLE,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "add role to user/client request initiated"
    )
    @PostMapping("/add-role")
    public ResponseEntity<AddRoleResponse> addRole(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam AddRoleToEnum addRoleTo,
            @RequestParam Long id,
            @RequestParam Long roleId) {
        return new ResponseEntity<>(roleService.addRole(addRoleTo, id, roleId), HttpStatus.CREATED);
    }
}
