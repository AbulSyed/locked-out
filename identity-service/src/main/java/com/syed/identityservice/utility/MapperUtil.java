package com.syed.identityservice.utility;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.AuditRequestEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.request.CreateUserRequest;

import java.time.LocalDateTime;

public class MapperUtil {

    public static AuditRequestEntity createInitialRequestEntity(String correlationId,
                                                                ProcessEnum process,
                                                                RequestTypeEnum requestType,
                                                                RequestStatusEnum requestStatus,
                                                                String log) {
        return AuditRequestEntity.builder()
                .correlationId(correlationId)
                .process(process)
                .requestType(requestType)
                .requestStatus(requestStatus)
                .log(log)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AuditRequestEntity fulfillRequestEntity(AuditRequestEntity request) {
        return AuditRequestEntity.builder()
                .correlationId(request.getCorrelationId())
                .process(request.getProcess())
                .requestType(request.getRequestType())
                .requestStatus(RequestStatusEnum.FULFILLED)
                .log(request.getLog())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AuditRequestEntity rejectRequestEntity(AuditRequestEntity request, String error) {
        return AuditRequestEntity.builder()
                .correlationId(request.getCorrelationId())
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
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserEntity mapUserModelToEntity(CreateUserRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
