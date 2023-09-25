package com.syed.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AppControllerIntegrationTest extends BaseTest {

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
                .description("desc")
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
                .andExpect(jsonPath("$.name", CoreMatchers.is(appRequest.getName())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(appRequest.getDescription())));
    }
}
