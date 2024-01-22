package com.syed.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syed.identityservice.domain.model.request.AppRequest;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AppControllerIntegrationTest extends ControllerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createApp() throws Exception {
        AppRequest createAppRequest = appRequest("app", "desc");

        ResultActions response = mockMvc.perform(post("/create-app")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_CORRELATION_ID, X_CORRELATION_VALUE)
                .content(objectMapper.writeValueAsString(createAppRequest)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(createAppRequest.getName())))
                .andExpect(jsonPath("$.description", is(createAppRequest.getDescription())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create-app.sql")
    void getApp() throws Exception {
        ResultActions response = mockMvc.perform(get("/get-app/100")
                .header(X_CORRELATION_ID, X_CORRELATION_VALUE));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("app5")))
                .andExpect(jsonPath("$.description", is("my app")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create-app.sql")
    void getAppList() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

        String cursor = dateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        ResultActions response = mockMvc.perform(get("/get-app-list?size=10&cursor=" + cursor)
                .header(X_CORRELATION_ID, X_CORRELATION_VALUE));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.apps", hasSize(1)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create-app.sql")
    void updateApp() throws Exception {
        AppRequest updateAppRequest = appRequest("updated app", "updated desc");

        ResultActions response = mockMvc.perform(put("/update-app/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_CORRELATION_ID, X_CORRELATION_VALUE)
                .content(objectMapper.writeValueAsString(updateAppRequest)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("updated app")))
                .andExpect(jsonPath("$.description", is("updated desc")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create-app.sql")
    void deleteApp() throws Exception {
        ResultActions response = mockMvc.perform(delete("/delete-app/100")
                .header(X_CORRELATION_ID, X_CORRELATION_VALUE));

        response.andDo(print())
                .andExpect(status().isOk());
    }
}
