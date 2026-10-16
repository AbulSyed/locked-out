package com.syed.authservice.controller;

import com.syed.authservice.clients.IdentityServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class TestController {

    private final IdentityServiceClient identityServiceClient;

    @GetMapping("/test1")
    public String test() {
        return "hi";
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        log.info("test2 invoked");
        var res =  identityServiceClient.getHealth();
        log.info("test2 finished");
        return res;
    }
}
