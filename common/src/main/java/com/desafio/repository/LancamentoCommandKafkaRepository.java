package com.desafio.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.desafio.model.Lancamento;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LancamentoCommandKafkaRepository implements LancamentoCommandRepository {
    final private KafkaTemplate<String, Lancamento> kafkaTemplate;
	@Value("${topic.name.lancamento}")
	private String topic;
    @Override
    public Lancamento save(Lancamento entity) {
        kafkaTemplate.send(topic, entity);
        kafkaTemplate.flush();
        return entity;
    }
    
}
