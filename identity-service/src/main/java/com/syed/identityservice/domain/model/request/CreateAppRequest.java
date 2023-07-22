package com.syed.identityservice.domain.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAppRequest {

    @NotEmpty(message = "Name can't be empty")
    private String name;
}