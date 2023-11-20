package com.desafio.model;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Consolidado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Consolidado {
    @Id
    LocalDate  data;
    @Column(nullable = false, precision = 17, scale = 2)
    BigDecimal saldo;
    @Column(nullable = false, precision = 17, scale = 2, name = "totalDebitos")
    BigDecimal totalDebitos;
    @Column(nullable = false, precision = 17, scale = 2, name = "totalCreditos")
    BigDecimal totalCreditos;
    @Column(nullable = false, name = "quantidadeDebitos")
    Long quantidadeDebitos;
    @Column(nullable = false, name = "quantidadeCreditos")
    Long quantidadeCreditos;
}
