package com.syed.authservice.controller;

import com.syed.authservice.clients.IdentityServiceClient;
import com.syed.authservice.domain.model.response.ClientResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class TestController {

    private final IdentityServiceClient identityServiceClient;

    @GetMapping("/test1")
    public String test() {
        return "hi";
    }

    @GetMapping("/identity/test2")
    public ResponseEntity<ClientResponse> test2() {
        return identityServiceClient.getClient("1", "app", "client");
    }
}
