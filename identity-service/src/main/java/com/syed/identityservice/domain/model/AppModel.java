package com.syed.identityservice.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AppModel {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
