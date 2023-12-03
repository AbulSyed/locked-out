package com.syed.authservice;

import com.syed.authservice.clients.IdentityServiceClient;
import com.syed.authservice.domain.model.response.ClientResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetClientIT extends BaseTest {

    @Autowired
    private IdentityServiceClient identityServiceClient;

    @Test
    void getClient() {
        ResponseEntity<ClientResponse> res = identityServiceClient.getClient("auth-service", "test", "client");

        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}
