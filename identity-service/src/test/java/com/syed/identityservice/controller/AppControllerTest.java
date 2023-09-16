package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.request.UpdateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.GetAppDetailsResponse;
import com.syed.identityservice.domain.model.response.GetAppResponse;
import com.syed.identityservice.domain.model.response.UpdateAppResponse;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AppControllerTest {

    @Mock
    private AppService appService;

    @InjectMocks
    private AppController appController;

    private String correlationId;
    private CreateAppRequest createAppRequest;
    private CreateAppResponse createAppResponse;
    private ResponseEntity<CreateAppResponse> createAppExpectedResponse;
    private GetAppResponse getAppResponse;
    private ResponseEntity<GetAppResponse> getAppExpectedResponse;
    private GetAppDetailsResponse getAppDetailsResponse;
    private ResponseEntity<GetAppDetailsResponse> getAppDetailsExpectedResponse;
    private List<GetAppResponse> getAppListResponse;
    private ResponseEntity<List<GetAppResponse>> getAppListExpectedResponse;
    private UpdateAppRequest updateAppRequest;
    private UpdateAppResponse updateAppResponse;
    private ResponseEntity<UpdateAppResponse> updateAppExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createAppRequest = CreateAppRequest.builder()
                .name("app")
                .description("test")
                .build();
        createAppResponse = CreateAppResponse.builder()
                .id(1L)
                .name("app")
                .description("test")
                .createdAt(LocalDateTime.now())
                .build();
        createAppExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createAppResponse);

        getAppResponse = GetAppResponse.builder()
                .id(1L)
                .name("app")
                .description("test")
                .createdAt(LocalDateTime.now())
                .build();
        getAppExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getAppResponse);

        getAppDetailsResponse = GetAppDetailsResponse.builder()
                .id(1L)
                .name("app")
                .description("test")
                .createdAt(LocalDateTime.now())
                .build();
        getAppDetailsExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getAppDetailsResponse);

        getAppListResponse = List.of(
                new GetAppResponse(
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
        updateAppResponse = UpdateAppResponse.builder()
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

        ResponseEntity<CreateAppResponse> res = appController.createApp(correlationId, createAppRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createAppExpectedResponse.getBody());
    }

    @Test
    void getApp() {
        when(appService.getApp(any(Long.class))).thenReturn(getAppResponse);

        ResponseEntity<GetAppResponse> res = appController.getApp(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppExpectedResponse.getBody());
    }

    @Test
    void getAppV2() {
        when(appService.getAppV2(any(Long.class))).thenReturn(getAppDetailsResponse);

        ResponseEntity<GetAppDetailsResponse> res = appController.getAppV2(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppDetailsExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppDetailsExpectedResponse.getBody());
    }

    @Test
    void getAppList() {
        when(appService.getAppList()).thenReturn(getAppListResponse);

        ResponseEntity<List<GetAppResponse>> res = appController.getAppList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppListExpectedResponse.getBody());
    }

    @Test
    void updateApp() {
        when(appService.updateApp(any(Long.class), any(UpdateAppRequest.class))).thenReturn(updateAppResponse);

        ResponseEntity<UpdateAppResponse> res = appController.updateApp(correlationId, 1L, updateAppRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), updateAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), updateAppExpectedResponse.getBody());
    }
}
