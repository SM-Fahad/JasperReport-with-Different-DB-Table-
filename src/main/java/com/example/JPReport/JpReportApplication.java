package com.example.JPReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class JpReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpReportApplication.class, args);
	}

}
