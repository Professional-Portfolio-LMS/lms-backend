package com.profport.lms.assignments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AssignmentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentsServiceApplication.class, args);
	}

}
