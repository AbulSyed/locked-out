package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientResponse;
import com.syed.identityservice.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ClientResponse> createClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId,
            @RequestBody ClientRequest request
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
    public ResponseEntity<ClientResponse> getClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long clientId
    ) {
        return new ResponseEntity<>(clientService.getClient(clientId), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get client list request initiated"
    )
    @GetMapping("/get-client-list")
    public ResponseEntity<List<ClientResponse>> getClientList(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId
    ) {
        return new ResponseEntity<>(clientService.getClientList(), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get client list by app id request initiated"
    )
    @GetMapping("/get-client-list-by-app")
    public ResponseEntity<List<ClientResponse>> getClientListByApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "appId", required = false) Long appId,
            @RequestParam(value = "appName", required = false) String appName
    ) {
        return new ResponseEntity<>(clientService.getClientListByApp(appId, appName), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.UPDATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "update client request initiated"
    )
    @PostMapping("/update-client/{clientId}")
    public ResponseEntity<ClientResponse> updateClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long clientId,
            @RequestBody ClientRequest request
            ) {
        return new ResponseEntity<>(clientService.updateClient(clientId, request), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.DELETE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "delete client request initiated"
    )
    @DeleteMapping("/delete-client/{clientId}")
    public void deleteClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long clientId
    ) {
        clientService.deleteClient(clientId);
    }
}
