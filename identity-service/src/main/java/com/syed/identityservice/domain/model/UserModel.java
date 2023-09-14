package com.syed.identityservice.domain.model;

import com.syed.identityservice.data.entity.AppEntity;
import com.syed.identityservice.data.entity.AuthorityEntity;
import com.syed.identityservice.data.entity.RoleEntity;

import java.time.LocalDateTime;
import java.util.Set;

public class UserModel {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private AppEntity userApp;
    private Set<RoleEntity> roles;
    private Set<AuthorityEntity> authorities;
    private LocalDateTime createdAt;
}
