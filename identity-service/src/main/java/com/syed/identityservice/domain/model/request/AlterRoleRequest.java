package com.syed.identityservice.domain.model.request;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlterRoleRequest {

    private Long userId;
    private Long clientId;
    private List<Long> roleIds;
}
