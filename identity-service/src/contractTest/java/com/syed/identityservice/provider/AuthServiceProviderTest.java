package com.syed.identityservice.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.syed.identityservice.ContractBaseTest;
import com.syed.identityservice.domain.model.response.ClientResponse;
import com.syed.identityservice.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("lockedOut_identityService")
//@PactBroker(url = "http://localhost:9292")
@PactFolder("src/contractTest/resources/pacts")
class AuthServiceProviderTest extends ContractBaseTest {

    @MockBean
    private ClientService clientService;

    @Value("${local.server.port}")
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        if (context != null) {
            context.verifyInteraction();
        }
    }

    @State("Get client by appName & clientId")
    public void getClient() {
        ClientResponse clientResponse = createClientResponse(1L, "client1", "secret", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), "http://", LocalDateTime.now());

        when(clientService.getClient(eq(null), any(String.class), any(String.class))).thenReturn(clientResponse);
    }
}