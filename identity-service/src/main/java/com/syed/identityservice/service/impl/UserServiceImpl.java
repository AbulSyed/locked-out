package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.UserService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AppRepository appRepository;

    @Override
    public Object createUser(Long appId, CreateUserRequest request) {
        AppEntity app = appRepository.findById(appId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId)));

        UserEntity user = MapperUtil.mapUserModelToEntity(request);
        user.setUserApp(app);

        return userRepository.save(user);
    }
}
