package com.tickethub.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    FlywayAutoConfiguration.class,
    KafkaAutoConfiguration.class
})
@ComponentScan(basePackages = "com.tickethub")
public class TickethubApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickethubApiApplication.class, args);
	}

}
