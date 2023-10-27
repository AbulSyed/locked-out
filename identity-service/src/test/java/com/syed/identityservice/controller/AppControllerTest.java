package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.request.UpdateAppRequest;
import com.syed.identityservice.domain.model.response.*;
import com.syed.identityservice.service.AppService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AppControllerTest {

    @Mock
    private AppService appService;

    @InjectMocks
    private AppController appController;

    private String correlationId;
    private CreateAppRequest createAppRequest;
    private AppResponse createAppResponse;
    private ResponseEntity<AppResponse> createAppExpectedResponse;
    private AppResponse getAppResponse;
    private ResponseEntity<AppResponse> getAppExpectedResponse;
    private AppV2Response getAppV2Response;
    private ResponseEntity<AppV2Response> getAppV2ExpectedResponse;
    private List<AppResponse> getAppListResponse;
    private ResponseEntity<List<AppResponse>> getAppListExpectedResponse;
    private UpdateAppRequest updateAppRequest;
    private AppResponse updateAppResponse;
    private ResponseEntity<AppResponse> updateAppExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createAppRequest = CreateAppRequest.builder()
                .name("app")
                .description("test")
                .build();
        createAppResponse = AppResponse.builder()
                .id(1L)
                .name("app")
                .description("test")
                .createdAt(LocalDateTime.now())
                .build();
        createAppExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createAppResponse);

        getAppResponse = AppResponse.builder()
                .id(1L)
                .name("app")
                .description("test")
                .createdAt(LocalDateTime.now())
                .build();
        getAppExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getAppResponse);

        getAppV2Response = AppV2Response.builder()
                .id(1L)
                .name("app")
                .description("test")
                .createdAt(LocalDateTime.now())
                .build();
        getAppV2ExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getAppV2Response);

        getAppListResponse = List.of(
                new AppResponse(
                        1L,
                        "app",
                        "test",
                        LocalDateTime.now()
                )
        );
        getAppListExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getAppListResponse);

        updateAppRequest = UpdateAppRequest.builder()
                .name("new name")
                .description("new desc")
                .build();
        updateAppResponse = AppResponse.builder()
                .id(1L)
                .name("new name")
                .description("new desc")
                .createdAt(LocalDateTime.now())
                .build();
        updateAppExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(updateAppResponse);
    }

    @Test
    void createApp() {
        when(appService.createApp(any(CreateAppRequest.class))).thenReturn(createAppResponse);

        ResponseEntity<AppResponse> res = appController.createApp(correlationId, createAppRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createAppExpectedResponse.getBody());
    }

    @Test
    void getApp() {
        when(appService.getApp(any(Long.class))).thenReturn(getAppResponse);

        ResponseEntity<AppResponse> res = appController.getApp(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppExpectedResponse.getBody());
    }

    @Test
    void getAppV2() {
        when(appService.getAppV2(any(Long.class))).thenReturn(getAppV2Response);

        ResponseEntity<AppV2Response> res = appController.getAppV2(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppV2ExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppV2ExpectedResponse.getBody());
    }

    @Test
    void getAppList() {
        when(appService.getAppList()).thenReturn(getAppListResponse);

        ResponseEntity<List<AppResponse>> res = appController.getAppList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppListExpectedResponse.getBody());
    }

    @Test
    void updateApp() {
        when(appService.updateApp(any(Long.class), any(UpdateAppRequest.class))).thenReturn(updateAppResponse);

        ResponseEntity<AppResponse> res = appController.updateApp(correlationId, 1L, updateAppRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), updateAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), updateAppExpectedResponse.getBody());
    }

    @Test
    void deleteApp() {
        appController.deleteApp(correlationId, any(Long.class));

        verify(appService, times(1)).deleteApp(any(Long.class));
    }
}
