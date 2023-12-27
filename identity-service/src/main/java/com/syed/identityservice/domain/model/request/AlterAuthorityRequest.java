package com.syed.identityservice.domain.model.request;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlterAuthorityRequest {

    private Long userId;
    private Long clientId;
    private List<Long> authorityIds;
}
