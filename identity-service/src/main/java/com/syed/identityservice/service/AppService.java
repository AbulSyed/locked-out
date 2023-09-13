package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.GetAppResponse;

public interface AppService {

    CreateAppResponse createApp(CreateAppRequest request);
    Object getApp(Long appId);
}
