package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.request.UpdateAppRequest;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;

import java.util.List;

public interface AppService {

    AppResponse createApp(CreateAppRequest request);
    AppResponse getApp(Long appId);
    AppV2Response getAppV2(Long appId);
    List<AppResponse> getAppList();
    AppResponse updateApp(Long appId, UpdateAppRequest request);
    void deleteApp(Long appId);
}
