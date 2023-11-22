package com.desafio.service;

import com.desafio.model.Lancamento;
import com.desafio.repository.LancamentoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LancamentoProcessadorService {
    private static Logger LOG = LoggerFactory.getLogger(LancamentoProcessadorService.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;
    @Value("${topic.name.lancamento}")
	private String intopic;

    @KafkaListener(topics = "${topic.name.lancamento}")
    public void listener(Lancamento message) {
        LOG.info("Lancamento recebido: {}", message.getData());
        lancamentoRepository.save(message);
    }

}
