package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.GetAppResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.AppService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

    @Override
    public CreateAppResponse createApp(CreateAppRequest request) {
        if (appRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        AppEntity appEntity = appRepository.save(MapperUtil.mapAppModelToEntity(request));

        return MapperUtil.mapAppEntityToCreateAppResponse(appEntity);
    }

    @Override
    public GetAppResponse getApp(Long appId) {
        AppEntity appEntity = appRepository.findById(appId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId))
        );

        return MapperUtil.mapAppEntityToGetAppResponse(appEntity);
    }
}
