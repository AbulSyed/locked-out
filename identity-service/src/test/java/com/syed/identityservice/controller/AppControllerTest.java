package com.syed.identityservice.controller;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.response.*;
import com.syed.identityservice.service.AppService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
class AppControllerTest extends BaseTest<Object> {

    @Mock
    private AppService appService;

    @InjectMocks
    private AppController appController;

    @Test
    void createApp() {
        AppRequest createAppRequest = createAppRequest("app", "test");
        AppResponse createAppResponse = createAppResponse(1L, "app", "test", LocalDateTime.now());
        ResponseEntity<Object> createAppExpectedResponse = createExpectedResponse(HttpStatus.CREATED, createAppResponse);

        when(appService.createApp(any(AppRequest.class))).thenReturn(createAppResponse);

        ResponseEntity<AppResponse> res = appController.createApp(correlationId, createAppRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createAppExpectedResponse.getBody());
    }

    @Test
    void getApp() {
        AppResponse getAppResponse = createAppResponse(1L, "app", "test", LocalDateTime.now());
        ResponseEntity<Object> getAppExpectedResponse = createExpectedResponse(HttpStatus.OK, getAppResponse);

        when(appService.getApp(any(Long.class))).thenReturn(getAppResponse);

        ResponseEntity<AppResponse> res = appController.getApp(correlationId, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppExpectedResponse.getBody());
    }

    @Test
    void getAppV2() {
        AppV2Response getAppV2Response = createAppV2Response(1L, "app", "test", LocalDateTime.now());
        ResponseEntity<Object> getAppV2ExpectedResponse = createExpectedResponse(HttpStatus.OK, getAppV2Response);

        when(appService.getAppV2(any(String.class))).thenReturn(getAppV2Response);

        ResponseEntity<AppV2Response> res = appController.getAppV2(correlationId, "test");

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppV2ExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppV2ExpectedResponse.getBody());
    }

    @Test
    void getAppList() {
        List<AppResponse> getAppListResponse = List.of(createAppResponse(1L, "app", "test", LocalDateTime.now()));
        AppPageResponse appPageResponse = createAppPageResponse(getAppListResponse, 1, 10, 5L, 1, true);
        ResponseEntity<Object> getAppListExpectedResponse = createExpectedResponse(HttpStatus.OK, appPageResponse);

        when(appService.getAppList(any(Integer.class), eq(null))).thenReturn(appPageResponse);

        ResponseEntity<AppPageResponse> res = appController.getAppList(correlationId, 1, null);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getAppListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getAppListExpectedResponse.getBody());
    }

    @Test
    void updateApp() {
        AppRequest updateAppRequest = createAppRequest("new name", "new desc");
        AppResponse updateAppResponse = createAppResponse(1L, "new name", "new desc", LocalDateTime.now());
        ResponseEntity<Object> updateAppExpectedResponse = createExpectedResponse(HttpStatus.OK, updateAppResponse);

        when(appService.updateApp(any(Long.class), any(AppRequest.class))).thenReturn(updateAppResponse);

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
