package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.GetAppResponse;

import java.util.List;

public interface AppService {

    CreateAppResponse createApp(CreateAppRequest request);
    GetAppResponse getApp(Long appId);
    List<GetAppResponse> getAppList();
}
