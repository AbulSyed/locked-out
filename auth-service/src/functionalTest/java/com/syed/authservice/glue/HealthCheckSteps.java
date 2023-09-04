package com.syed.authservice.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class HealthCheckSteps {

    @LocalServerPort
    private String port;
    private final RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;

    @When("a user calls the Health Check endpoint {string}")
    public void callHealthCheckEndpoint(String endpoint) {
        response = restTemplate.exchange("http://localhost:" + port + endpoint, HttpMethod.GET, null, String.class);
    }

    @Then("the response status code should be {string}")
    public void verifyResponseStatusCode(String status) {
        String statusCode = response.getStatusCode().toString();
        assertEquals(status, statusCode);
    }

    @And("the response should contain the message {string}")
    public void verifyResponse(String message) {
        String responseBody = response.getBody();
        assertEquals(message, responseBody);
    }
}
