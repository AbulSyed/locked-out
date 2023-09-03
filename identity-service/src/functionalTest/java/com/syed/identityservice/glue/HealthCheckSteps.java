package com.syed.identityservice.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
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

    @Given("the API is running locally")
    public void setBaseUri() {

    }

    @When("a user calls the Health Check endpoint")
    public void callHealthCheckEndpoint() {
        response = restTemplate.exchange("http://localhost:" + port + "/health", HttpMethod.GET, null, String.class);
    }

    @Then("the response status code should be 200 OK")
    public void verifyResponseStatusCode() {
        String statusCode = response.getStatusCode().toString();
        assertEquals("200 OK", statusCode);
    }

    @And("the response body should contain the message 'Identity service v2023082139 running'")
    public void verifyResponseBody() {
        String responseBody = response.getBody();
        String expectedMessage = "Identity service v2023082139 running";
        assertEquals(expectedMessage, responseBody);
    }
}
