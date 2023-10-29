package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.*;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.service.AuthorityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AuthorityController {

    private final AuthorityService authorityService;

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.AUTHORITY,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "create authority request initiated"
    )
    @PostMapping("/create-authority")
    public ResponseEntity<AuthorityResponse> createAuthority(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @Valid @RequestBody AuthorityRequest request) {
        return new ResponseEntity<>(authorityService.createAuthority(request), HttpStatus.CREATED);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.AUTHORITY,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "add authority to user/client request initiated"
    )
    @PostMapping("/add-authority")
    public ResponseEntity<MessageResponse> addAuthority(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam AuthorityToEnum addAuthorityTo,
            @RequestParam Long id,
            @RequestParam Long authorityId) {
        return new ResponseEntity<>(authorityService.addAuthority(addAuthorityTo, id, authorityId), HttpStatus.CREATED);
    }
}
