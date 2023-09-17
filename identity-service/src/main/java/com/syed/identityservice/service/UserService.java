package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;

public interface UserService {

    CreateUserResponse createUser(Long appId, CreateUserRequest request);
}
