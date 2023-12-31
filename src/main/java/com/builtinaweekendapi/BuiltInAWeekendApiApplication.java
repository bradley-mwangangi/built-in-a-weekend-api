package com.builtinaweekendapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BuiltInAWeekendApiApplication {
	// TODO - configure login on web. login form not displaying. throws 403
	public static void main(String[] args) {
		SpringApplication.run(BuiltInAWeekendApiApplication.class, args);
	}
}
