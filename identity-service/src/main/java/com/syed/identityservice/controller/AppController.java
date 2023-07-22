package com.syed.identityservice.controller;

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

    @PostMapping("/create-app")
    public ResponseEntity<CreateAppResponse> createApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @Valid @RequestBody CreateAppRequest request) {
        return new ResponseEntity<>(appService.createApp(correlationId, request), HttpStatus.OK);
    }
}
