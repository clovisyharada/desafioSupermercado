package com.desafio.consolidado.controller;

import com.desafio.model.Consolidado;
import com.desafio.service.ConsolidadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/consolidados")
public class ConsolidadoController {

    @Autowired
    private ConsolidadoService consolidadoService;

    @GetMapping("/byDateRange")
    public List<Consolidado> getConsolidadosInDateRange(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate) {
        return consolidadoService.getConsolidadosInDateRange(startDate, endDate);
    }
}
