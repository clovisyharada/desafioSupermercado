package com.desafio.repository;
import com.desafio.model.Lancamento;

public interface LancamentoCommandRepository {
    Lancamento save(Lancamento entity);
}
