package com.syed.identityservice.domain.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetAppResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
