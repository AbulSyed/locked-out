package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateClientRequest;
import com.syed.identityservice.domain.model.response.CreateClientResponse;
import com.syed.identityservice.domain.model.response.GetClientResponse;
import com.syed.identityservice.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ClientController {

    private final ClientService clientService;

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "create client request initiated"
    )
    @PostMapping("/create-client/{appId}")
    public ResponseEntity<CreateClientResponse> createClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId,
            @RequestBody CreateClientRequest request
    ) {
        return new ResponseEntity<>(clientService.createClient(appId, request), HttpStatus.CREATED);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get client request initiated"
    )
    @GetMapping("/get-client/{clientId}")
    public ResponseEntity<GetClientResponse> getClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long clientId
    ) {
        return new ResponseEntity<>(clientService.getClient(clientId), HttpStatus.OK);
    }
}
