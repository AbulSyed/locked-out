package com.syed.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.AppRequest;
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
public class AppControllerIntegrationTest extends ControllerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        appRepository.deleteAll();
    }

    @Test
    void createApp() throws Exception {
        AppRequest createAppRequest = createAppRequest("app", "desc");

        ResultActions response = mockMvc.perform(post("/create-app")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_CORRELATION_ID, X_CORRELATION_VALUE)
                .content(objectMapper.writeValueAsString(createAppRequest)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is(createAppRequest.getName())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(createAppRequest.getDescription())));
    }
}
