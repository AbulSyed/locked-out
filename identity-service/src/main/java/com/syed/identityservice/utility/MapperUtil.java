package com.syed.identityservice.utility;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.AuditRequestEntity;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateAppRequest;

import java.time.LocalDateTime;

public class MapperUtil {

    public static AuditRequestEntity createAuditRequestEntity(String correlationId,
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

    public static AppEntity mapAppModelToEntity(CreateAppRequest request) {
        return AppEntity.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
