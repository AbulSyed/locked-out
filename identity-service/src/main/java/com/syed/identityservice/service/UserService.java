package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.domain.model.response.GetUserResponse;

import java.util.List;

public interface UserService {

    CreateUserResponse createUser(Long appId, CreateUserRequest request);
    GetUserResponse getUser(Long userId);
    List<GetUserResponse> getUserList();
}