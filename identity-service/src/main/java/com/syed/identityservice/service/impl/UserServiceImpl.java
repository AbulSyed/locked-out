package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.request.UpdateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.domain.model.response.GetUserResponse;
import com.syed.identityservice.domain.model.response.UpdateUserResponse;
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
    public CreateUserResponse createUser(Long appId, CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Username"));
        }

        AppEntity app = appRepository.findById(appId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));

        UserEntity user = MapperUtil.mapUserModelToEntity(request);
        user.setUserApp(app);

        return MapperUtil.mapUserEntityToCreateUserResponse(userRepository.save(user));
    }

    @Override
    public GetUserResponse getUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + userId)));

        return MapperUtil.mapUserEntitytoGetUserResponse(user);
    }

    @Override
    public List<GetUserResponse> getUserList() {
        List<UserEntity> userEntityList = userRepository.findAll();

        return MapperUtil.mapUserEntityListToGetUserResponseList(userEntityList);
    }

    @Override
    public List<GetUserResponse> getUserListByAppId(Long appId) {
        AppEntity app = appRepository.findById(appId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));

        List<UserEntity> userEntityList = userRepository.getUserEntitiesByUserApp(app);

        return MapperUtil.mapUserEntityListToGetUserResponseList(userEntityList);
    }

    @Override
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
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

        return MapperUtil.mapUserEntitytoUpdateUserResponse(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User with id " + userId))
        );

        userRepository.delete(userEntity);
    }
}
