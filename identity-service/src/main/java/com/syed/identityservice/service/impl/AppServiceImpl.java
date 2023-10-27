package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.AppService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

    @Override
    public AppResponse createApp(AppRequest request) {
        if (appRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        AppEntity appEntity = appRepository.save(MapperUtil.mapAppModelToEntity(request));

        return MapperUtil.mapAppEntityToAppResponse(appEntity);
    }

    @Override
    public AppResponse getApp(Long appId) {
        AppEntity appEntity = appRepository.findById(appId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId))
        );

        return MapperUtil.mapAppEntityToAppResponse(appEntity);
    }

    @Override
    public AppV2Response getAppV2(Long appId) {
        AppEntity appEntity = appRepository.findById(appId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId))
        );

        return MapperUtil.mapAppEntityToAppV2Response(appEntity);
    }

    @Override
    public List<AppResponse> getAppList() {
        List<AppEntity> appEntityList = appRepository.findAll();

        return MapperUtil.mapAppEntityListToAppListResponse(appEntityList);
    }

    @Override
    public AppResponse updateApp(Long appId, AppRequest request) {
        AppEntity appEntity = appRepository.findById(appId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId))
        );

        if (!request.getName().equals(appEntity.getName()) && appRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        appEntity.setName(request.getName());
        appEntity.setDescription(request.getDescription());

        appRepository.save(appEntity);

        return MapperUtil.mapAppEntityToAppResponse(appEntity);
    }

    @Override
    public void deleteApp(Long appId) {
        AppEntity appEntity = appRepository.findById(appId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage("App with id " + appId))
        );

        appRepository.delete(appEntity);
    }
}
