package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.AuditRequestEntity;
import com.syed.identityservice.data.repository.AppRepository;
import com.syed.identityservice.data.repository.AuditRequestRepository;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.service.AppService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AppServiceImpl implements AppService {

    private final AuditRequestRepository requestRepository;
    private final AppRepository appRepository;

    @Override
    public CreateAppResponse createApp(String correlationId, CreateAppRequest request) {
        AuditRequestEntity initialRequestEntity = MapperUtil.createInitialRequestEntity(
                correlationId, ProcessEnum.APP, RequestTypeEnum.CREATE, RequestStatusEnum.PENDING, "");
        requestRepository.save(initialRequestEntity);

        AppEntity appEntity = MapperUtil.mapAppModelToEntity(request);
        appRepository.save(appEntity);

        AuditRequestEntity fulfillRequestEntity = MapperUtil.fulfillRequestEntity(initialRequestEntity);
        requestRepository.save(fulfillRequestEntity);

        return CreateAppResponse.builder()
                .id(appEntity.getId())
                .name(appEntity.getName())
                .build();
    }
}
