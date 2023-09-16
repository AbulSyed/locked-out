package com.syed.identityservice.domain.model.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateAppRequest {

    @Size(max = 15, message = "Maximum length of app name is 15")
    private String name;
    private String description;
}
