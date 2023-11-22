package com.desafio.consolidado.controller;

import com.desafio.model.Consolidado;
import com.desafio.service.ConsolidadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsolidadoController.class)
class ConsolidadoControllerTest {

    @MockBean
    private ConsolidadoService consolidadoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetConsolidadosInDateRange() throws Exception {
        // Arrange
        LocalDate endDate = LocalDate.of(2023, 10, 13);
        LocalDate dataConsolidado1 = LocalDate.of(2023, 10, 10);
        LocalDate dataConsolidado2 = LocalDate.of(2023, 10, 5);
        LocalDate startDate = LocalDate.of(2023, 10, 1);

        Consolidado consolidado1 = new Consolidado(dataConsolidado1, BigDecimal.valueOf(1231), BigDecimal.valueOf(1231), BigDecimal.valueOf(1231), 10L, 10L);
        Consolidado consolidado2 = new Consolidado(dataConsolidado2, BigDecimal.valueOf(1232), BigDecimal.valueOf(1231), BigDecimal.valueOf(1231), 10L, 10L);
        List<Consolidado> consolidados = Arrays.asList(consolidado1, consolidado2);

        when(consolidadoService.getConsolidadosInDateRange(startDate, endDate)).thenReturn(consolidados);

        // Act & Assert
        mockMvc.perform(get("/consolidados/byDateRange")
                .param("startDate", startDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .param("endDate", endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].data").exists())
                .andExpect(jsonPath("$[1].data").exists())
                .andExpect(jsonPath("$[2].data").doesNotExist());
    }
}
