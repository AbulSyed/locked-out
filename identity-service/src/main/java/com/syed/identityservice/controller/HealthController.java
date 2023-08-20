package com.syed.identityservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Identity service " + appVersion + " running", HttpStatus.OK);
    }
}
