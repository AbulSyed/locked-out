package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.response.AppPageResponse;
import com.syed.identityservice.domain.model.response.AppResponse;
import com.syed.identityservice.domain.model.response.AppV2Response;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.exception.custom.ResourceNotFoundException;
import com.syed.identityservice.service.AppService;
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
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

    private static final String APP_WITH_ID = "App with id ";
    private static final String APP_WITH_NAME = "App with name ";

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
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(APP_WITH_ID + appId))
        );

        return MapperUtil.mapAppEntityToAppResponse(appEntity);
    }

    @Override
    public AppV2Response getAppV2(String appName) {
        AppEntity appEntity = appRepository.findByName(appName);

        if (appEntity == null) {
            throw new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(APP_WITH_NAME + appName));
        }

        return MapperUtil.mapAppEntityToAppV2Response(appEntity);
    }

    @Override
    public AppPageResponse getAppList(int size, String cursor) {
        Pageable pageable = Utility.createPageable(1, size, Sort.by(Sort.DEFAULT_DIRECTION, "createdAt"));
        Page<AppEntity> appEntityPage;

        if (cursor == null) {
            appEntityPage = appRepository.findAll(pageable);
        } else {
            LocalDateTime formattedCursor = LocalDateTime.parse(cursor, DateTimeFormatter.ISO_DATE_TIME);
            appEntityPage = appRepository.findAllByCreatedAtGreaterThan(formattedCursor, pageable);
        }

        List<AppEntity> appEntityList = appEntityPage.getContent();

        List<AppResponse> appResponseList = MapperUtil.mapAppEntityListToAppListResponse(appEntityList);

        return MapperUtil.mapAppListResponseToAppPageResponse(
                appResponseList,
                appEntityPage.getNumber() + 1,
                appEntityPage.getSize(),
                appEntityPage.getTotalElements(),
                appEntityPage.getTotalPages(),
                appEntityPage.isLast()
        );
    }

    @Override
    public AppResponse updateApp(Long appId, AppRequest request) {
        AppEntity appEntity = appRepository.findById(appId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(APP_WITH_ID + appId))
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
                () -> new ResourceNotFoundException(ErrorConstant.RESOURCE_NOT_FOUND.formatMessage(APP_WITH_ID + appId))
        );

        appRepository.delete(appEntity);
    }
}
