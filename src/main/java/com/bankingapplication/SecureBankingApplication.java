package com.bankingapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition (
		info = @Info(
				title = "Secure Banking Appliction",
				description = "Backend REST APIs for Secure Banking Application",
				version = "v1.0",
				contact = @Contact (
						name = "Subham Subhakanta Rout",
						email = "subham.rout2023@gmail.com",
						url = "https://github.com/solocoder9/SecureBankApp"
				),
				license = @License (
						name = "Subham Subhakanta Rout",
						url = "https://github.com/solocoder9"
				)
				
		),
		externalDocs = @ExternalDocumentation (
				description =  "The Secure Banking Application Documentation",
				url = "https://github.com/solocoder9/SecureBankApp"
				)
)
public class SecureBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureBankingApplication.class, args);
	}

}
