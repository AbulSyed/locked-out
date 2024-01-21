package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2PageResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.UserService;
import com.syed.identityservice.utility.MapperUtil;
import com.syed.identityservice.utility.Utility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AppRepository appRepository;

    private static final String USER_WITH_ID = "User with id ";

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
    public UserV2Response getUser(Long userId, String appName, String username) {
        UserEntity user = null;

        if (userId != null) {
            user = userRepository.findById(userId).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(USER_WITH_ID + userId)));
        } else if (appName == null && username != null) {
            user = userRepository.findByUsername(username);

            if (user == null) {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("Username " + username));
            }
        } else if (appName != null && username != null) {
            AppEntity app = appRepository.findByName(appName);

            if (app == null) {
                throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with name " + appName));
            } else {
                user = userRepository.getUserEntityByUserAppAndUsername(app, username);

                if (user == null) {
                    throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("User " + username + " with app " + appName));
                }
            }
        }

        return MapperUtil.mapUserEntityToUserV2Response(user);
    }

    @Override
    public UserV2PageResponse getUserList(int size, String cursor) {
        Pageable pageable = Utility.createPageable(1, size, Sort.by(Sort.DEFAULT_DIRECTION, "createdAt"));
        Page<UserEntity> userEntityPage;

        if (cursor == null) {
            userEntityPage = userRepository.findAll(pageable);
        } else {
            LocalDateTime formattedCursor = LocalDateTime.parse(cursor, DateTimeFormatter.ISO_DATE_TIME);
            userEntityPage = userRepository.findAllByCreatedAtGreaterThan(formattedCursor, pageable);
        }

        List<UserEntity> userEntityList = userEntityPage.getContent();

        List<UserV2Response> userV2ResponseList = MapperUtil.mapUserEntityListToUserV2ResponseList(userEntityList);

        return MapperUtil.mapUserV2ResponseListToUserV2PageResponse(
                userV2ResponseList,
                userEntityPage.getNumber() + 1,
                userEntityPage.getSize(),
                userEntityPage.getTotalElements(),
                userEntityPage.getTotalPages(),
                userEntityPage.isLast()
        );
    }

    @Override
    public UserV2PageResponse getUserListByApp(Long appId, String appName, int size, String cursor) {
        Pageable pageable = Utility.createPageable(1, size, Sort.by(Sort.DEFAULT_DIRECTION, "createdAt"));

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

        Page<UserEntity> userEntityPage;

        if (cursor == null) {
            userEntityPage = userRepository.getUserEntitiesByUserApp(app, pageable);
        } else {
            LocalDateTime formattedCursor = LocalDateTime.parse(cursor, DateTimeFormatter.ISO_DATE_TIME);
            userEntityPage = userRepository.getUserEntitiesByUserAppAndCreatedAtGreaterThan(app, formattedCursor, pageable);
        }

        List<UserEntity> userEntityList = userEntityPage.getContent();

        List<UserV2Response> userV2ResponseList = MapperUtil.mapUserEntityListToUserV2ResponseList(userEntityList);

        return MapperUtil.mapUserV2ResponseListToUserV2PageResponse(
                userV2ResponseList,
                userEntityPage.getNumber() + 1,
                userEntityPage.getSize(),
                userEntityPage.getTotalElements(),
                userEntityPage.getTotalPages(),
                userEntityPage.isLast()
        );
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(USER_WITH_ID + userId))
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
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(USER_WITH_ID + userId))
        );

        userRepository.delete(userEntity);
    }
}
