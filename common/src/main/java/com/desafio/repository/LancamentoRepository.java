package com.desafio.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.desafio.model.ConsolidacaoLancamentos;
import com.desafio.model.Lancamento;

@Repository
public interface LancamentoRepository extends ListCrudRepository<Lancamento, Long> {
    List<Lancamento> findByDataBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT SUM(m.valor) as valorTotal, COUNT(1) as quantidade FROM Lancamento m WHERE m.tipo = ?1 AND m.data >= ?2 AND m.data < ?3")
    List<ConsolidacaoLancamentos> calcularConsolidado(
        String tipo,
        LocalDateTime startDate,
        LocalDateTime endDate
    );
    @Query("SELECT MIN(m.data) FROM  Lancamento m")
    LocalDateTime findMinData();
}
