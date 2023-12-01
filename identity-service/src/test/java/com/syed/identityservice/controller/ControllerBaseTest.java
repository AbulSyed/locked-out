package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.domain.model.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ControllerBaseTest<T> {

    protected String correlationId = "1";

    protected AppRequest createAppRequest(String name, String description) {
        return AppRequest.builder()
                .name(name)
                .description(description)
                .build();
    }

    protected AppResponse createAppResponse(Long id, String name, String description, LocalDateTime createdAt) {
        return AppResponse.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    protected ResponseEntity<T> createExpectedResponse(HttpStatus status, T body) {
        return ResponseEntity.status(status).body(body);
    }

    protected AppV2Response createAppV2Response(Long id, String name, String description, LocalDateTime createdAt) {
        return AppV2Response.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    protected AuthorityRequest createAuthorityRequest(String name) {
        return AuthorityRequest.builder()
                .name(name)
                .build();
    }

    protected AuthorityResponse createAuthorityResponse(Long id, String name) {
        return AuthorityResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    protected MessageResponse createMessageResponse(String message) {
        return MessageResponse.builder()
                .message(message)
                .build();
    }
}
