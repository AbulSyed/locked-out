package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.ResponseWrapper;
import com.syed.identityservice.domain.model.response.ResponseStatus;

public interface AppService {

    ResponseWrapper<CreateAppResponse, ResponseStatus> createApp(CreateAppRequest request);
}
