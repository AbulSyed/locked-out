package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.service.AppService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class AppControllerTest {

    @Mock
    private AppService appService;

    @InjectMocks
    private AppController appController;

    private String correlationId;
    private CreateAppRequest createAppRequest;
    private CreateAppResponse createAppResponse;
    private ResponseEntity<CreateAppResponse> expectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createAppRequest = CreateAppRequest.builder()
                .name("app")
                .build();

        createAppResponse = CreateAppResponse.builder()
                .id(1L)
                .name("app")
                .createdAt(LocalDateTime.now())
                .build();

        expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createAppResponse);
    }

    @Test
    void createApp_Success() {
        when(appService.createApp(any(CreateAppRequest.class))).thenReturn(createAppResponse);

        ResponseEntity<CreateAppResponse> res = appController.createApp(correlationId, createAppRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(res.getBody(), expectedResponse.getBody());
    }
}
