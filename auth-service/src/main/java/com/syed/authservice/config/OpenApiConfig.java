package com.syed.authservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "LockedOut - Auth service",
                description = "LockedOut - Auth service API documentation",
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
                        url = "http://localhost:8080"
                ),
//                add another env
//                @Server(
//                        description = "UAT",
//                        url = "http://localhost:8080/uat"
//                )
        }
)
public class OpenApiConfig {
}
