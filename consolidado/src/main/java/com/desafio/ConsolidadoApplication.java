package com.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.desafio"})
public class ConsolidadoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsolidadoApplication.class, args);
	}
}
