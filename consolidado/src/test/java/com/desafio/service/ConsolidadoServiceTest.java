package com.desafio.service;

import com.desafio.model.Consolidado;
import com.desafio.repository.ConsolidadoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ConsolidadoServiceTest {

    @Mock
    private ConsolidadoRepository consolidadoRepository;

    @InjectMocks
    private ConsolidadoService consolidadoService;

    @Test
    void testGetConsolidadoByDate() {
        // Arrange
        LocalDate date = LocalDate.now();
        Consolidado consolidado = new Consolidado();
        consolidado.setData(date);

        when(consolidadoRepository.findById(date)).thenReturn(Optional.of(consolidado));

        // Act
        Optional<Consolidado> result = consolidadoService.getConsolidadoByDate(date);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(date, result.get().getData());
    }

    @Test
    void testGetAllConsolidados() {
        // Arrange
        Consolidado consolidado1 = new Consolidado();
        Consolidado consolidado2 = new Consolidado();
        List<Consolidado> consolidados = Arrays.asList(consolidado1, consolidado2);

        when(consolidadoRepository.findAll()).thenReturn(consolidados);

        // Act
        List<Consolidado> result = consolidadoService.getAllConsolidados();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(consolidado1));
        assertTrue(result.contains(consolidado2));
    }

    @Test
    void testSaveConsolidado() {
        // Arrange
        Consolidado consolidado = new Consolidado();
        consolidado.setData(LocalDate.now());
        consolidado.setSaldo(BigDecimal.TEN);
        consolidado.setTotalCreditos(BigDecimal.TEN);
        consolidado.setTotalDebitos(BigDecimal.TEN);

        when(consolidadoRepository.save(consolidado)).thenReturn(consolidado);

        // Act
        Consolidado result = consolidadoService.saveConsolidado(consolidado);

        // Assert
        assertNotNull(result);
        assertEquals(consolidado.getData(), result.getData());
        assertEquals(consolidado.getSaldo(), result.getSaldo());
    }

    @Test
    void testDeleteConsolidadoByDate() {
        // Arrange
        LocalDate date = LocalDate.now();

        when(consolidadoRepository.existsById(date)).thenReturn(true);

        // Act
        consolidadoService.deleteConsolidadoByDate(date);

        // Assert
        verify(consolidadoRepository, times(1)).deleteById(date);
    }

    @Test
    void testGetConsolidadosInDateRange() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 10);
        Consolidado consolidado1 = new Consolidado();
        Consolidado consolidado2 = new Consolidado();
        List<Consolidado> consolidados = Arrays.asList(consolidado1, consolidado2);

        when(consolidadoRepository.findByDataBetween(startDate, endDate)).thenReturn(consolidados);

        // Act
        List<Consolidado> result = consolidadoService.getConsolidadosInDateRange(startDate, endDate);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(consolidado1));
        assertTrue(result.contains(consolidado2));
    }
    @Test
    void testDeleteConsolidadoByDate_EntityNotFoundException() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 10, 10);

        when(consolidadoRepository.existsById(date)).thenReturn(false);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> consolidadoService.deleteConsolidadoByDate(date));
    }
}
