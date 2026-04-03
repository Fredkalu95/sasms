package com.sasms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SasmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SasmsApplication.class, args);
	}

}
