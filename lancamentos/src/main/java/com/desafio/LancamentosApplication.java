package com.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.desafio"})
public class LancamentosApplication {
	public static void main(String[] args) {
		SpringApplication.run(LancamentosApplication.class, args);
	}
}
