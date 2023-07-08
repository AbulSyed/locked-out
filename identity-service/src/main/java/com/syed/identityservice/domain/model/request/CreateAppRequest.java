package com.syed.identityservice.domain.model.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAppRequest {

    private String name;
}