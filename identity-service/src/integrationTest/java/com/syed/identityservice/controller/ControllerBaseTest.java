package com.syed.identityservice.controller;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.domain.model.request.AppRequest;

public class ControllerBaseTest extends BaseTest {

    protected static final String X_CORRELATION_ID = "x-correlation-id";
    protected static final Integer X_CORRELATION_VALUE = 1;

    protected AppRequest createAppRequest(String name, String description) {
        return AppRequest.builder()
                .name(name)
                .description(description)
                .build();
    }
}
