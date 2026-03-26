package com.tickethub.api;

import org.springframework.boot.SpringApplication;

public class TestTickethubApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(TickethubApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
