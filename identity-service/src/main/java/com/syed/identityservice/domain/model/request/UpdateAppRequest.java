package com.syed.identityservice.domain.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateAppRequest {

    @NotEmpty(message = "Name can't be empty")
    @Size(max = 15, message = "Maximum length of app name is 15")
    private String name;
    @NotEmpty(message = "Description can't be empty")
    private String description;
}
