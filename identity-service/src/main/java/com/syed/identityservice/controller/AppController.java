package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.service.AppService;
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
        return new ResponseEntity<>(appService.createApp(correlationId, request), HttpStatus.OK);
    }
}
