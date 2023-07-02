package com.example.api_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ApiAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAssignmentApplication.class, args);
	}

}
