package com.syed.authservice;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseTest {

    @BeforeEach
    void setup() {
        // Manually initialize & configure WireMock it to use the correct resource path
        WireMockServer wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig()
                        .port(8081)
                        .usingFilesUnderDirectory("src/integrationTest/resources")
        );

        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }
}
