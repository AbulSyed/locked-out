package com.syed.identityservice.domain.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateAppResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
