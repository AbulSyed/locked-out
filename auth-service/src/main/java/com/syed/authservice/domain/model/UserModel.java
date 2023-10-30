package com.syed.authservice.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserModel {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Set<RoleModel> roles;
    private Set<AuthorityModel> authorities;
    private LocalDateTime createdAt;
}
