package com.syed.authservice;

import com.syed.authservice.domain.model.response.ClientResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetClientIntegrationTest extends BaseTest {

    @Test
    void getClient() {
        ResponseEntity<ClientResponse> res = identityServiceClient.getClient("auth-service", "test", "client");

        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}
