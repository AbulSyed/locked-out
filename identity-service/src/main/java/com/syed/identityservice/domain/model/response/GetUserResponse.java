package com.syed.identityservice.domain.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetUserResponse {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
