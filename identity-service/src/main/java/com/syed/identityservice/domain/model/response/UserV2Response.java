package com.syed.identityservice.domain.model.response;

import com.syed.identityservice.domain.model.AuthorityModel;
import com.syed.identityservice.domain.model.RoleModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserV2Response {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Set<RoleModel> roles;
    private Set<AuthorityModel> authorities;
    private LocalDateTime createdAt;
}
