package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateRoleRequest;
import com.syed.identityservice.domain.model.response.CreateRoleResponse;
import com.syed.identityservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<CreateRoleResponse> createRole(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @Valid @RequestBody CreateRoleRequest request) {
        return new ResponseEntity<>(roleService.createRole(request), HttpStatus.CREATED);
    }
}
