package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.response.CreateAppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health controller", description = "Identity service health endpoint to check status")
@RestController
public class HealthController {

    @Value("${app.version}")
    private String appVersion;

    @Operation(summary = "Health endpoint for status check")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Identity service " + appVersion + " running", HttpStatus.OK);
    }
}
