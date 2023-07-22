package com.syed.authservice.controller;

import lombok.extern.slf4j.Slf4j;
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
        return new ResponseEntity<>("Auth service " + appVersion + " running", HttpStatus.OK);
    }
}
