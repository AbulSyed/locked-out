package com.syed.identityservice.domain.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateRoleRequest {

    @NotEmpty(message = "Name can't be empty")
    @Size(max = 15, message = "Maximum length of app name is 15")
    private String name;
}
