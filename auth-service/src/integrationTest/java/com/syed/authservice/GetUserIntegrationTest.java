package com.syed.authservice;

import com.syed.authservice.domain.model.response.UserV2Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserIntegrationTest extends BaseTest {

    @Test
    void getUser() {
        ResponseEntity<UserV2Response> res = identityServiceClient.getUser("auth-service", null, null, "abul");

        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}
