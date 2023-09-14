package com.syed.identityservice.domain.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthorityModel {

    private Long id;
    private String name;
}