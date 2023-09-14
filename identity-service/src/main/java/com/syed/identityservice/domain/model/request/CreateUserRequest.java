package com.syed.identityservice.domain.model.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserRequest {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
