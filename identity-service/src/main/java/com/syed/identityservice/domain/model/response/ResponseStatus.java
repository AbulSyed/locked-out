package com.syed.identityservice.domain.model.response;

import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class ResponseStatus {

    private ProcessEnum process;
    private RequestTypeEnum requestType;
    private RequestStatusEnum requestStatus;
    private String log;
}
