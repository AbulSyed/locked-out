package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.CreateUserRequest;

public interface UserService {

    Object createUser(Long appId, CreateUserRequest request);
}
