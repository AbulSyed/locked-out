package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.ResponseStatus;
import com.syed.identityservice.domain.model.response.ResponseWrapper;
import com.syed.identityservice.service.AppService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class AppController {

    private final AppService appService;

    @PostMapping("/create-app")
    public ResponseEntity<ResponseWrapper<CreateAppResponse, ResponseStatus>> createApp(@RequestBody CreateAppRequest request) {
        log.info("Entering AppController:createApp");

        return new ResponseEntity<>(appService.createApp(request), HttpStatus.OK);
    }
}
