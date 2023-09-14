package com.syed.identityservice.domain.model.response;

import com.syed.identityservice.domain.model.UserModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetAppDetailsResponse {

    private Long id;
    private String name;
    private String description;
    private Set<UserModel> users;
    // TODO add clients set
    private LocalDateTime createdAt;
}
