package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.UserService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AppRepository appRepository;

    @Override
    public UserResponse createUser(Long appId, UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Username"));
        }

        AppEntity app = appRepository.findById(appId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));

        UserEntity user = MapperUtil.mapUserModelToEntity(request);
        user.setUserApp(app);

        return MapperUtil.mapUserEntityToUserResponse(userRepository.save(user));
    }

    @Override
    public UserV2Response getUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + userId)));

        return MapperUtil.mapUserEntityToUserV2Response(user);
    }

    @Override
    public List<UserV2Response> getUserList() {
        List<UserEntity> userEntityList = userRepository.findAll();

        return MapperUtil.mapUserEntityListToUserV2ResponseList(userEntityList);
    }

    @Override
    public List<UserV2Response> getUserListByApp(Long appId, String appName) {
        AppEntity app = null;

        if (appId != null) {
            app = appRepository.findById(appId).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));
        } else if (appName != null) {
            app = appRepository.findByName(appName);
            if (app == null) {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with name " + appName));
            }
        }

        List<UserEntity> userEntityList = userRepository.getUserEntitiesByUserApp(app);

        return MapperUtil.mapUserEntityListToUserV2ResponseList(userEntityList);
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + userId))
        );

        if (!request.getUsername().equals(userEntity.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Username"));
        }

        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());
        userEntity.setEmail(request.getEmail());
        userEntity.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(userEntity);

        return MapperUtil.mapUserEntityToUserResponse(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + userId))
        );

        userRepository.delete(userEntity);
    }
}
