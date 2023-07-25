package com.syed.identityservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "LockedOut - Identity service",
                description = "LockedOut - Identity service API documentation",
                version = "1.0",
                contact = @Contact(
                        name = "Abul Syed",
                        email = "google.co.uk",
                        url = "https:google.co.uk"
                )
        ),
        servers = {
                @Server(
                        description = "DEV",
                        url = "http://localhost:8081"
                )
        }
)
public class OpenApiConfig {
}
