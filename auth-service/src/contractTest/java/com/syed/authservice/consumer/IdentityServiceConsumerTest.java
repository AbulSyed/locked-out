package com.syed.authservice.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.*;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syed.authservice.domain.model.response.ClientResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArray;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// https://docs.pact.io/implementation_guides/jvm/consumer/junit5
@SpringBootTest
@PactConsumerTest
//@ExtendWith(PactConsumerTestExt.class) - alternative for `@PactConsumerTest`
@PactTestFor(providerName = "lockedOut_identityService")
//@MockServerConfig(hostInterface = "localhost", port = "1234")
class IdentityServiceConsumerTest {

    @Autowired
    private ObjectMapper mapper;

    @Pact(provider = "lockedOut_identityService", consumer = "lockedOut_authService")
    public V4Pact getClientPact(PactDslWithProvider builder) {
        return builder
                .given("Get client by appName & clientId")
                .uponReceiving("A requests for a client by appName & clientId")
                .path("/get-client")
                .query("appName=myapp&clientId=client1")
                .headers("x-correlation-id", "123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(buildGetClientPact())
                .toPact(V4Pact.class);
    }

    @Test
    void test(MockServer mockServer) throws JsonProcessingException {
        System.out.println(mockServer.getUrl());

        String url = mockServer.getUrl() + "/get-client?appName=myapp&clientId=client1";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-correlation-id", "123");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        List<ClientResponse> clientResponseList = mapper.readValue(response.getBody(), new TypeReference<List<ClientResponse>>() {});

        assertNotNull(clientResponseList);
    }

    private DslPart buildGetClientPact() {
        return newJsonArray(o -> o.object(ob -> ob
                .numberType("id", 1)
                .stringType("clientId", "client1")
                .stringType("clientSecret", "secret")
                .array("roles", a -> {})
                .array("authorities", a -> {})
                .array("scopes", a -> {})
                .array("authMethod", a -> {})
                .array("authGrantType", a -> {})
                .stringType("redirectUri", "http://127.0.0.1:3000/authorized")
                .stringType("createdAt", "2023-11-30T12:26:55.732213")
        )).build();
    }
}
