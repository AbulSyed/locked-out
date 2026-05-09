package com.syed.authservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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
