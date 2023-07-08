package com.syed.identityservice.service.impl;

import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.ResponseStatus;
import com.syed.identityservice.domain.model.response.ResponseWrapper;
import com.syed.identityservice.service.AppService;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {

    @Override
    public ResponseWrapper<CreateAppResponse, ResponseStatus> createApp(CreateAppRequest request) {
        return null;
    }
}
