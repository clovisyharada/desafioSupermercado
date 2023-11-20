package com.desafio.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConsolidacaoLancamentosImpl implements ConsolidacaoLancamentos {
    BigDecimal valorTotal;
    Long quantidade;
}
