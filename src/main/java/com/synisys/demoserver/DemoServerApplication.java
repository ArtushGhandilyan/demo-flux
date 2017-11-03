package com.synisys.demoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class DemoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServerApplication.class, args);
	}
}