package com.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class LancamentoProcessadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LancamentoProcessadorApplication.class, args);
	}

}
