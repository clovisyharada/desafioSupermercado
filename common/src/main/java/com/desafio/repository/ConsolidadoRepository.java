package com.desafio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.desafio.model.Consolidado;

@Repository
public interface ConsolidadoRepository extends ListCrudRepository<Consolidado, LocalDate> {
    List<Consolidado> findByDataBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT c FROM Consolidado c WHERE c.data = (SELECT MAX(cc.data) FROM Consolidado cc)")
    Consolidado findLastConsolidado();
    @Query("SELECT c FROM Consolidado c WHERE c.data = (SELECT MAX(cc.data) FROM Consolidado cc WHERE cc.data < ?1)")
    Consolidado findLastConsolidadoBeforeData(LocalDate maxDate);
}
