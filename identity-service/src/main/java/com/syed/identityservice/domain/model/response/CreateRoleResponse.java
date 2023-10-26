package com.syed.identityservice.domain.model.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateRoleResponse {

    private Long id;
    private String name;
}
