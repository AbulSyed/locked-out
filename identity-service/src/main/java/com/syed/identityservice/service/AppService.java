package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.response.AppPageResponse;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;

public interface AppService {

    AppResponse createApp(AppRequest request);
    AppResponse getApp(Long appId);
    AppV2Response getAppV2(String appName);
    AppPageResponse getAppList(int size, String cursor);
    AppResponse updateApp(Long appId, AppRequest request);
    void deleteApp(Long appId);
}
