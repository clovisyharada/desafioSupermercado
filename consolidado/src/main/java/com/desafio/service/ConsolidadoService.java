package com.desafio.service;

import com.desafio.model.Consolidado;
import com.desafio.repository.ConsolidadoRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsolidadoService {

    private final ConsolidadoRepository consolidadoRepository;

    public ConsolidadoService(ConsolidadoRepository consolidadoRepository) {
        this.consolidadoRepository = consolidadoRepository;
    }

    public Optional<Consolidado> getConsolidadoByDate(LocalDate date) {
        return consolidadoRepository.findById(date);
    }

    public List<Consolidado> getConsolidadosInDateRange(LocalDate startDate, LocalDate endDate) {
        return consolidadoRepository.findByDataBetween(startDate, endDate);
    }
    
    public List<Consolidado> getAllConsolidados() {
        return consolidadoRepository.findAll();
    }

    public Consolidado saveConsolidado(Consolidado consolidado) {
        return consolidadoRepository.save(consolidado);
    }

    public void deleteConsolidadoByDate(LocalDate date) {
        if (consolidadoRepository.existsById(date)) {
            consolidadoRepository.deleteById(date);
        } else {
            throw new EntityNotFoundException("Consolidado with date " + date + " not found");
        }
    }
    public Consolidado getLastConsolidado() {
        return consolidadoRepository.findLastConsolidado();
    }
    public Consolidado getLastConsolidadoUntilData(LocalDate maxDate) {
        return consolidadoRepository.findLastConsolidadoBeforeData(maxDate);
    }
}
