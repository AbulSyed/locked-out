package com.syed.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.info("Entering HealthController:health");

        return new ResponseEntity<>("Auth server " + appVersion + " running", HttpStatus.OK);
    }
}
