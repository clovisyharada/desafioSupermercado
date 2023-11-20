package com.desafio.lancamentos.controller;

import com.desafio.model.Lancamento;
import com.desafio.service.LancamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LancamentoController.class)
class LancamentoControllerTest {

    @MockBean
    private LancamentoService lancamentoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testNovoLancamento() throws Exception {
        // Arrange
        Lancamento lancamento = new Lancamento();
        lancamento.setData(LocalDateTime.now());
        lancamento.setDescricao("Test Lancamento");
        lancamento.setValor(new BigDecimal("100.50"));
        lancamento.setTipo("Crédito");

        when(lancamentoService.saveLancamento(any(Lancamento.class))).thenReturn(lancamento);


        // Act & Assert
        mockMvc.perform(post("/lancamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lancamento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Test Lancamento"))
                .andExpect(jsonPath("$.tipo").value("Crédito"));
    }

    @Test
    void testNovoLancamento_InternalServerError() throws Exception {
        // Arrange
        Lancamento lancamento = new Lancamento();
        lancamento.setData(LocalDateTime.now());
        lancamento.setDescricao("Test Lancamento");
        lancamento.setValor(new BigDecimal("100.50"));
        lancamento.setTipo("Crédito");

        when(lancamentoService.saveLancamento(any(Lancamento.class))).thenThrow(new RuntimeException("Internal Server Error"));

        // Act & Assert
        mockMvc.perform(post("/lancamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lancamento)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllLancamentos() throws Exception {
        // Arrange
        LocalDateTime data = LocalDateTime.now();
        Lancamento lancamento1 = new Lancamento(1L, data, "Test Lancamento", new BigDecimal("100.50"), "Crédito");
        Lancamento lancamento2 = new Lancamento(2L, data, "Test Lancamento 2", new BigDecimal("101.50"), "Dédito");
        List<Lancamento> lancamentos = Arrays.asList(lancamento1, lancamento2);

        when(lancamentoService.getAllLancamentos()).thenReturn(lancamentos);

        // Act & Assert
        mockMvc.perform(get("/lancamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].data").exists())
                .andExpect(jsonPath("$[1].data").exists());
    }
}
