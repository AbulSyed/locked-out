package com.syed.identityservice.domain.model.response;

import com.syed.identityservice.domain.enums.RequestStatus;
import com.syed.identityservice.domain.enums.RequestType;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class ResponseStatus {

    private RequestType requestType;
    private RequestStatus requestStatus;
    private String log;
}
