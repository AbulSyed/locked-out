package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.domain.model.response.GetUserResponse;

public interface UserService {

    CreateUserResponse createUser(Long appId, CreateUserRequest request);
    GetUserResponse getUser(Long userId);
}
