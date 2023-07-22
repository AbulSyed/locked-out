package com.syed.identityservice.utility;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.RequestEntity;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateAppRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class MapperUtil {

    public static RequestEntity createInitialRequestEntity(String correlationId,
                                                            ProcessEnum process,
                                                            RequestTypeEnum requestType,
                                                            RequestStatusEnum requestStatus,
                                                            String log) {
        return RequestEntity.builder()
                .requestId(correlationId)
                .process(process)
                .requestType(requestType)
                .requestStatus(requestStatus)
                .log(log)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static RequestEntity fulfillRequestEntity(RequestEntity request) {
        return RequestEntity.builder()
                .requestId(request.getRequestId())
                .process(request.getProcess())
                .requestType(request.getRequestType())
                .requestStatus(RequestStatusEnum.FULFILLED)
                .log(request.getLog())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static RequestEntity rejectRequestEntity(RequestEntity request, String error) {
        return RequestEntity.builder()
                .requestId(request.getRequestId())
                .process(request.getProcess())
                .requestType(request.getRequestType())
                .requestStatus(RequestStatusEnum.REJECTED)
                .log(error)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AppEntity mapAppModelToEntity(CreateAppRequest request) {
        return AppEntity.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
