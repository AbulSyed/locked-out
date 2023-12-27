package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.AlterRoleRequest;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;
import com.syed.identityservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            log = "alter roles of user/client request initiated"
    )
    @PutMapping("/alter-roles")
    public ResponseEntity<MessageResponse> alterRoles(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam RoleToEnum addRoleTo,
            @RequestBody AlterRoleRequest alterRoleRequest) {
        return new ResponseEntity<>(roleService.alterRoles(addRoleTo, alterRoleRequest), HttpStatus.CREATED);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.ROLE,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get list of roles"
    )
    @GetMapping("/get-role-list")
    public ResponseEntity<List<String>> getRoleList(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId
    ) {
        return new ResponseEntity<>(roleService.getRoleList(), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.ROLE,
            requestType = RequestTypeEnum.DELETE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "delete role from user/client request initiated"
    )
    @DeleteMapping("/delete-role-from")
    public void deleteRoleFrom(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam RoleToEnum deleteRoleFrom,
            @RequestParam Long id,
            @RequestParam Long roleId) {
        roleService.deleteRoleFrom(deleteRoleFrom, id, roleId);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.ROLE,
            requestType = RequestTypeEnum.DELETE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "delete role request initiated"
    )
    @DeleteMapping("/delete-role/{roleId}")
    public void deleteRole(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long roleId) {
        roleService.deleteRole(roleId);
    }
}
