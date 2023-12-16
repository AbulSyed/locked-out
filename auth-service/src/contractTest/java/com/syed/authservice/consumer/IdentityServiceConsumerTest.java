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
import com.syed.authservice.clients.IdentityServiceClient;
import com.syed.authservice.domain.model.response.ClientResponse;
import com.syed.authservice.domain.model.response.UserV2Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// https://docs.pact.io/implementation_guides/jvm/consumer/junit5
@SpringBootTest
@PactConsumerTest
//@ExtendWith(PactConsumerTestExt.class) - alternative for `@PactConsumerTest`
@PactTestFor(providerName = "lockedOut_identityService")
@MockServerConfig(hostInterface = "localhost", port = "8081")
class IdentityServiceConsumerTest {

    @Autowired
    private IdentityServiceClient identityServiceClient;

    @Pact(provider = "lockedOut_identityService", consumer = "lockedOut_authService")
    public V4Pact getClientPact(PactDslWithProvider builder) {
        return builder
                .given("Get client by appName & clientId")
                .uponReceiving("A request for a client by appName & clientId")
                .path("/get-client")
                .query("appName=myapp&clientId=client1")
                .headers("x-correlation-id", "123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(buildGetClientPact())
                .toPact(V4Pact.class);
    }

    @Pact(provider = "lockedOut_identityService", consumer = "lockedOut_authService")
    public V4Pact getUserPact(PactDslWithProvider builder) {
        return builder
                .given("Get user by username")
                .uponReceiving("A request for a user by username")
                .path("/get-user")
                .query("username=admin")
                .headers("x-correlation-id", "123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(buildGetUserPact())
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getClientPact", pactVersion = PactSpecVersion.V4)
    void getClientTest(MockServer mockServer) {
        ResponseEntity<ClientResponse> res = identityServiceClient.getClient("123", "myapp", "client1");

        ClientResponse clientResponse = res.getBody();

        assertNotNull(clientResponse);
    }

    @Test
    @PactTestFor(pactMethod = "getUserPact", pactVersion = PactSpecVersion.V4)
    void getUserTest(MockServer mockServer) {
        ResponseEntity<UserV2Response> res = identityServiceClient.getUser("123", null, null, "admin");

        UserV2Response userV2Response = res.getBody();

        assertNotNull(userV2Response);
    }

    // docs: https://docs.pact.io/implementation_guides/jvm/consumer#building-json-bodies-with-pactdsljsonbody-dsl
    // examples: https://docs.pact.io/implementation_guides/jvm/consumer#examples
    private DslPart buildGetClientPact() {
        return newJsonBody((o) -> {
            o.numberType("id", 1);
            o.stringType("clientId", "client1");
            o.stringType("clientSecret", "secret");
            o.array("roles", a -> {});
            o.array("authorities", a -> {});
            o.array("scopes", a -> {});
            o.array("authMethod", a -> {});
            o.array("authGrantType", a -> {});
            o.stringType("redirectUri", "http://127.0.0.1:3000/authorized");
            o.stringType("createdAt", "2023-11-30T12:26:55.732213");
        }).build();
    }

    private DslPart buildGetUserPact() {
        return newJsonBody((o) -> {
            o.numberType("id", 1);
            o.stringType("username", "admin");
            o.stringType("password", "admin");
            o.stringType("email", "a@m.com");
            o.stringType("phoneNumber", "07123123123");
            o.array("roles", a -> {});
            o.array("authorities", a -> {});
            o.stringType("createdAt", "2023-11-30T12:26:55.732213");
        }).build();
    }
}
