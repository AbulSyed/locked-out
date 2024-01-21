package com.syed.identityservice.service;

import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2PageResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;

public interface UserService {

    UserResponse createUser(Long appId, UserRequest request);
    UserV2Response getUser(Long userId, String appName, String username);
    UserV2PageResponse getUserList(int size, String cursor);
    UserV2PageResponse getUserListByApp(Long appId, String appName, int size, String cursor);
    UserResponse updateUser(Long userId, UserRequest request);
    void deleteUser(Long userId);
}
