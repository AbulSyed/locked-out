package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.FieldAlreadyExistsException;
import com.syed.identityservice.service.AppService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

    @Override
    public CreateAppResponse createApp(String correlationId, CreateAppRequest request) {
        if (appRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        AppEntity appEntity = MapperUtil.mapAppModelToEntity(request);
        appRepository.save(appEntity);

        return CreateAppResponse.builder()
                .id(appEntity.getId())
                .name(appEntity.getName())
                .build();
    }
}
