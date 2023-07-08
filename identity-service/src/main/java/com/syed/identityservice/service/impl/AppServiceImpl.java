package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.RequestEntity;
import com.syed.identityservice.data.repository.RequestRepository;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.ResponseStatus;
import com.syed.identityservice.domain.model.response.ResponseWrapper;
import com.syed.identityservice.service.AppService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class AppServiceImpl implements AppService {

    private final RequestRepository requestRepository;

    @Override
    public ResponseWrapper<CreateAppResponse, ResponseStatus> createApp(CreateAppRequest request) {
        log.info("Entering AppServiceImpl:createApp");

        RequestEntity requestEntity = MapperUtil.createInitialRequestEntity(ProcessEnum.APP, RequestTypeEnum.CREATE, RequestStatusEnum.PENDING, "");
        requestRepository.save(requestEntity);

        return null;
    }
}
