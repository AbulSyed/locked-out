package com.syed.identityservice;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdentityServiceApplication {

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(IdentityServiceApplication.class, args);
	}

}
