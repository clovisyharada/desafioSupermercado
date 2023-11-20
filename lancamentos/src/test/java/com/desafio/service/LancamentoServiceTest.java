package com.desafio.service;

import com.desafio.model.Lancamento;
import com.desafio.repository.LancamentoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LancamentoServiceTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    @InjectMocks
    private LancamentoService lancamentoService;

    @Test
    void testGetLancamentoById() {
        // Arrange
        long id = 1L;
        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);

        when(lancamentoRepository.findById(id)).thenReturn(Optional.of(lancamento));

        // Act
        Optional<Lancamento> result = lancamentoService.getLancamentoById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void testGetAllLancamentos() {
        // Arrange
        Lancamento lancamento1 = new Lancamento();
        Lancamento lancamento2 = new Lancamento();
        List<Lancamento> lancamentos = Arrays.asList(lancamento1, lancamento2);

        when(lancamentoRepository.findAll()).thenReturn(lancamentos);

        // Act
        List<Lancamento> result = lancamentoService.getAllLancamentos();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(lancamento1));
        assertTrue(result.contains(lancamento2));
    }

    @Test
    void testSaveLancamento() {
        // Arrange
        Lancamento lancamento = new Lancamento();

        when(lancamentoRepository.save(lancamento)).thenReturn(lancamento);

        // Act
        Lancamento result = lancamentoService.saveLancamento(lancamento);

        // Assert
        assertNotNull(result);
        assertEquals(lancamento, result);
    }

    @Test
    void testDeleteLancamentoById() {
        // Arrange
        long id = 1L;

        when(lancamentoRepository.existsById(id)).thenReturn(true);

        // Act
        lancamentoService.deleteLancamentoById(id);

        // Assert
        verify(lancamentoRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteLancamentoById_EntityNotFoundException() {
        // Arrange
        long id = 1L;

        when(lancamentoRepository.existsById(id)).thenReturn(false);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> lancamentoService.deleteLancamentoById(id));
    }
}
