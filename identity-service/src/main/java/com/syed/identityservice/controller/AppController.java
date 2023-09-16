package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.GetAppDetailsResponse;
import com.syed.identityservice.domain.model.response.GetAppResponse;
import com.syed.identityservice.service.AppService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CreateAppResponse> createApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @Valid @RequestBody CreateAppRequest request) {
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
    public ResponseEntity<GetAppResponse> getApp(
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
    @GetMapping("/get-app-v2/{appId}")
    public ResponseEntity<GetAppDetailsResponse> getAppV2(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId
    ) {
        return new ResponseEntity<>(appService.getAppV2(appId), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.APP,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get app list request initiated"
    )
    @GetMapping("/get-app-list")
    public ResponseEntity<List<GetAppResponse>> getAppList(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId
    ) {
        return new ResponseEntity<>(appService.getAppList(), HttpStatus.OK);
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
