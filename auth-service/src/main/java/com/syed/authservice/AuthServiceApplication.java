package com.syed.authservice;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
