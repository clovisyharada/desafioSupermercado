package com.desafio;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.desafio.service.ConsolidadorService;

@SpringBootApplication
public class ConsolidadorApplication implements CommandLineRunner {
   @Autowired ConsolidadorService consolidadorService;

    private static Logger LOG = LoggerFactory.getLogger(ConsolidadorApplication.class);

	public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(ConsolidadorApplication.class, args);
        LOG.info("APPLICATION FINISHED");
	}
    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");
 
        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
		if (args.length > 0) {
			// se passou a data como parametro, usa a data e neste caso, recalcula tudo dessa data até ontem
			// formato 2023-11-16
			LocalDate inicio = LocalDate.parse(args[0]);
			consolidadorService.consolidaAPartirDe(inicio);
		} else {
			// sem parâmetro, usa a data de ontem e neste caso, não recalcula
			consolidadorService.consolida();
		}
    }
}
