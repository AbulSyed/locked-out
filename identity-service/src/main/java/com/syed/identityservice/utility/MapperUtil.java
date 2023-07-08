package com.syed.identityservice.utility;

import com.syed.identityservice.data.entity.RequestEntity;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public class MapperUtil {

    public static RequestEntity createInitialRequestEntity(ProcessEnum process,
                                                           RequestTypeEnum requestType,
                                                           RequestStatusEnum requestStatus,
                                                           String log) {
        return RequestEntity.builder()
                .requestId(UUID.randomUUID().toString())
                .process(process)
                .requestType(requestType)
                .requestStatus(requestStatus)
                .log(log)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
