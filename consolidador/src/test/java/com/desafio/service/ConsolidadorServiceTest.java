package com.desafio.service;

import com.desafio.model.ConsolidacaoLancamentos;
import com.desafio.model.ConsolidacaoLancamentosImpl;
import com.desafio.model.Consolidado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class ConsolidadorServiceTest {

    @Mock
    private ConsolidadoService consolidadoService;

    @Mock
    private LancamentoService lancamentoService;

    @InjectMocks
    private ConsolidadorService consolidadorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsolidaAPartirDe() {
        // Mocking data
        LocalDate inicio = LocalDate.of(2023, 1, 1);
        Consolidado anterior = new Consolidado(inicio.minusDays(1), BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ZERO, 0L, 0L);

        // Mocking behavior
        when(consolidadoService.getLastConsolidadoUntilData(inicio)).thenReturn(anterior);

        // Test method
        consolidadorService.consolidaAPartirDe(inicio);

        // Verification
        verify(consolidadoService, times(1)).getLastConsolidadoUntilData(inicio);
    }

    @Test
    void testConsolida() {
        // Mocking data
        LocalDate ontem = LocalDate.of(2023, 1, 2);
        Consolidado anterior = new Consolidado(ontem.minusDays(1), BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ZERO, 0L, 0L);

        // Mocking behavior
        when(consolidadoService.getLastConsolidado()).thenReturn(anterior);
        when(lancamentoService.obterPrimeiraData()).thenReturn(LocalDateTime.of(2023, 1, 1, 0, 0, 0));

        // Test method
        consolidadorService.consolida();

        // Verification
        verify(consolidadoService, times(1)).getLastConsolidado();
    }

    @Test
    void testConsolidaDia() {
        // Mocking data
        LocalDate data = LocalDate.of(2023, 1, 1);
        BigDecimal saldo = BigDecimal.ZERO;
        ConsolidacaoLancamentos deb = new ConsolidacaoLancamentosImpl(BigDecimal.ONE, 1L);
        ConsolidacaoLancamentos cred = new ConsolidacaoLancamentosImpl(BigDecimal.TEN, 2L);

        // Mocking behavior
        when(lancamentoService.calcularConsolidado(data, "débito")).thenReturn(deb);
        when(lancamentoService.calcularConsolidado(data, "crédito")).thenReturn(cred);

        // Test method
        //BigDecimal result = 
        consolidadorService.consolidaDia(data, saldo);

        // Verification
        verify(lancamentoService, times(1)).calcularConsolidado(data, "débito");
        verify(lancamentoService, times(1)).calcularConsolidado(data, "crédito");

    }
}
