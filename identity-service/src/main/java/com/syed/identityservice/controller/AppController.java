package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.response.AppPageResponse;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;
import com.syed.identityservice.service.AppService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AppController {

    private final AppService appService;

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.APP,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "create app request initiated"
    )
    @PostMapping("/create-app")
    public ResponseEntity<AppResponse> createApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @Valid @RequestBody AppRequest request) {
        return new ResponseEntity<>(appService.createApp(request), HttpStatus.CREATED);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.APP,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get app request initiated"
    )
    @GetMapping("/get-app/{appId}")
    public ResponseEntity<AppResponse> getApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId
    ) {
        return new ResponseEntity<>(appService.getApp(appId), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.APP,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get app request v2 initiated"
    )
    @GetMapping("/get-app-v2/{appName}")
    public ResponseEntity<AppV2Response> getAppV2(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable String appName
    ) {
        return new ResponseEntity<>(appService.getAppV2(appName), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.APP,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get app list request initiated"
    )
    @GetMapping("/get-app-list")
    public ResponseEntity<AppPageResponse> getAppList(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "size", required = true) int size,
            @RequestParam(value = "cursor", required = false) String cursor
    ) {
        return new ResponseEntity<>(appService.getAppList(size, cursor), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.APP,
            requestType = RequestTypeEnum.UPDATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "update app request initiated"
    )
    @PutMapping("/update-app/{appId}")
    public ResponseEntity<AppResponse> updateApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId,
            @Valid @RequestBody AppRequest request
    ) {
        return new ResponseEntity<>(appService.updateApp(appId, request), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.APP,
            requestType = RequestTypeEnum.DELETE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "delete app request initiated"
    )
    @DeleteMapping("/delete-app/{appId}")
    public void deleteApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId
    ) {
        appService.deleteApp(appId);
    }
}
