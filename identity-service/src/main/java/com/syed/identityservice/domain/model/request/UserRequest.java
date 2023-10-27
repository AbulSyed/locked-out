package com.syed.identityservice.domain.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotEmpty(message = "Username can't be empty")
    @Size(max = 15, message = "Maximum length of username is 15")
    private String username;
    @NotEmpty(message = "Password can't be empty")
    private String password;
    @NotEmpty(message = "Email can't be empty")
    private String email;
    private String phoneNumber;
}
