package com.desafio.service;

import com.desafio.model.ConsolidacaoLancamentos;
import com.desafio.model.Lancamento;
import com.desafio.repository.LancamentoRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;

    public LancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    public Optional<Lancamento> getLancamentoById(long id) {
        return lancamentoRepository.findById(id);
    }

    public List<Lancamento> getAllLancamentos() {
        return lancamentoRepository.findAll();
    }

    public Lancamento saveLancamento(Lancamento lancamento) {
        return lancamentoRepository.save(lancamento);
    }

    public void deleteLancamentoById(long id) {
        if (lancamentoRepository.existsById(id)) {
            lancamentoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Lancamento with id " + id + " not found");
        }
    }
    public List<Lancamento> getLancamentosForDate(LocalDate date) {
        LocalDateTime startDateTime = obterHoraZero(date);
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        return lancamentoRepository.findByDataBetween(startDateTime, endDateTime);
    }
    public ConsolidacaoLancamentos calcularConsolidado(LocalDate date, String tipo) {
        LocalDateTime startDateTime = obterHoraZero(date);
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        return lancamentoRepository.calcularConsolidado(tipo, startDateTime, endDateTime).get(0);
    }
    private LocalDateTime obterHoraZero(LocalDate date) {
        OffsetDateTime off = OffsetDateTime.of(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),0,0,0,0, ZoneOffset.UTC);
        ZonedDateTime zoned = off.atZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
        LocalDateTime startDateTime = zoned.toLocalDateTime();
        return startDateTime;
    }
    public LocalDateTime obterPrimeiraData() {
        return lancamentoRepository.findMinData();
    }
}
