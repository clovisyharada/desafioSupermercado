package com.desafio.lancamentos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.model.Lancamento;
import com.desafio.service.LancamentoService;

@RestController
public class LancamentoController {
   @Autowired
   LancamentoService lancamentoService;

   @RequestMapping(value="/lancamentos", method=RequestMethod.POST)
   public ResponseEntity<Lancamento> novoLancamento(@RequestBody Lancamento lancamento) {
      try {
      Lancamento lancamento2 = lancamentoService.saveLancamento(lancamento);
      return new ResponseEntity<>(lancamento2, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   }
   @RequestMapping(value="/lancamentos", method=RequestMethod.GET)
   List<Lancamento> all() {
    List<Lancamento> a = lancamentoService.getAllLancamentos();
    return a;
  }
}
