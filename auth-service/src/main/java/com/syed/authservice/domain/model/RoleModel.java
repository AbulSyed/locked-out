package com.syed.authservice.domain.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoleModel {

    private Long id;
    private String name;
}
