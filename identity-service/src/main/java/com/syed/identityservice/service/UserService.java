package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;

import java.util.List;

public interface UserService {

    UserResponse createUser(Long appId, UserRequest request);
    UserV2Response getUser(Long userId);
    List<UserV2Response> getUserList();
    List<UserV2Response> getUserListByApp(Long appId, String appName);
    UserResponse updateUser(Long userId, UserRequest request);
    void deleteUser(Long userId);
}
