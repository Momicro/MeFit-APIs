package com.experis.de.MeFit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MeFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeFitApplication.class, args);
	}

}
