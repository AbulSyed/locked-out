package com.syed.identityservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class AppControllerIntegrationTest {

    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.2-alpine");

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private CreateAppRequest appRequest;

    private static final String X_CORRELATION_ID = "x-correlation-id";
    private static final Integer X_CORRELATION_VALUE = 1;

    @BeforeEach
    void setUp() {
        appRepository.deleteAll();

        appRequest = CreateAppRequest.builder()
                .name("app")
                .build();
    }

    @Test
    void createApp() throws Exception {
        ResultActions response = mockMvc.perform(post("/create-app")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_CORRELATION_ID, X_CORRELATION_VALUE)
                .content(objectMapper.writeValueAsString(appRequest)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is("app2")));
    }
}
