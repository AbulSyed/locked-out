package com.syed.identityservice.domain.model.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateAppResponse {
    
    private Long id;
    private String name;
}
