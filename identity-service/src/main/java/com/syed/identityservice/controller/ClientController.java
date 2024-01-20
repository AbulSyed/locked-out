package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.response.ClientPageResponse;
import com.syed.identityservice.domain.model.response.ClientResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.InvalidRequestException;
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
    @GetMapping("/get-client")
    public ResponseEntity<ClientResponse> getClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "appName", required = false) String appName,
            @RequestParam(value = "clientId", required = false) String clientId
    ) {
        if ((id == null && appName != null && clientId == null)
                || (id == null && appName == null)) {
            throw new InvalidRequestException(ErrorConstant.INVALID_REQUEST.getValue());
        }

        return new ResponseEntity<>(clientService.getClient(id, appName, clientId), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get client list request initiated"
    )
    @GetMapping("/get-client-list")
    public ResponseEntity<ClientPageResponse> getClientList(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "size", required = true) int size,
            @RequestParam(value = "cursor", required = false) String cursor
    ) {
        return new ResponseEntity<>(clientService.getClientList(size, cursor), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get client list by app request initiated"
    )
    @GetMapping("/get-client-list-by-app")
    public ResponseEntity<ClientPageResponse> getClientListByApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "appId", required = false) Long appId,
            @RequestParam(value = "appName", required = false) String appName,
            @RequestParam(value = "size", required = true) int size,
            @RequestParam(value = "cursor", required = false) String cursor
    ) {
        if (appId == null && appName == null) {
            throw new InvalidRequestException(ErrorConstant.INVALID_REQUEST.getValue());
        }

        return new ResponseEntity<>(clientService.getClientListByApp(appId, appName, size, cursor), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.CLIENT,
            requestType = RequestTypeEnum.UPDATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "update client request initiated"
    )
    @PutMapping("/update-client/{clientId}")
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
