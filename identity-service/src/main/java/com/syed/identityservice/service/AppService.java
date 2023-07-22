package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;

public interface AppService {

    CreateAppResponse createApp(String correlationId, CreateAppRequest request);
}
