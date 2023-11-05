package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.ScopeRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.service.ScopeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ScopeController {

    private final ScopeService scopeService;

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.SCOPE,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "alter client scope request initiated"
    )
    @PostMapping("/alter-client-scopes/{id}")
    public ResponseEntity<MessageResponse> alterClientScopes(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long id,
            @Valid @RequestBody ScopeRequest request) {
        return new ResponseEntity<>(scopeService.alterClientScopes(id, request), HttpStatus.OK);
    }
}
