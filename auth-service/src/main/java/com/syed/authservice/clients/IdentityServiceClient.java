package com.syed.authservice.clients;

import com.syed.authservice.domain.model.response.ClientResponse;
import com.syed.authservice.domain.model.response.UserV2Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "identityserviceclient",
        url = "${feign.client.config.identityService.url}"
)
public interface IdentityServiceClient {

    @GetMapping("/get-client")
    ResponseEntity<ClientResponse> getClient(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "appName", required = false) String appName,
            @RequestParam(value = "clientId", required = false) String clientId
    );

    @GetMapping("/get-user")
    ResponseEntity<UserV2Response> getUser(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "appName", required = false) String appName,
            @RequestParam(value = "username", required = false) String username
    );
}
