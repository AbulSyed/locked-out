package com.syed.identityservice.domain.model.response;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class ResponseWrapper<T, E> {

    private T response;
    private E status;
}
