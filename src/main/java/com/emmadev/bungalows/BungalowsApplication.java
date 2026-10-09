package com.emmadev.bungalows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BungalowsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BungalowsApplication.class, args);
	}

}
