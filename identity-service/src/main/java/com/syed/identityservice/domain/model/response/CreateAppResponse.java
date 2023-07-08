package com.syed.identityservice.domain.model.response;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class CreateAppResponse {
    
    private Long id;
    private String name;
}
