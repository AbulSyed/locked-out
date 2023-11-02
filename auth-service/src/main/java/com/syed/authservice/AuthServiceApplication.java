package com.syed.authservice;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.syed.authservice")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class AuthServiceApplication {

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
