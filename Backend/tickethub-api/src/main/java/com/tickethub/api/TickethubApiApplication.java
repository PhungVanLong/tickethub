package com.tickethub.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TickethubApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickethubApiApplication.class, args);
	}

}
