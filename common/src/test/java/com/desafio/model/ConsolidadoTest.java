package com.desafio.model;

import org.junit.jupiter.api.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConsolidadoTest {

    @Test
    void testGetterAndSetter() {
        Consolidado consolidado = new Consolidado();

        LocalDate data = LocalDate.of(2023, 10, 13);
        BigDecimal valor = new BigDecimal("1000.50");

        consolidado.setData(data);
        consolidado.setSaldo(valor);

        assertEquals(data, consolidado.getData());
        assertEquals(valor, consolidado.getSaldo());
    }

    @Test
    void testAnnotations() {
        assertTrue(Consolidado.class.isAnnotationPresent(Entity.class));
        assertTrue(Consolidado.class.isAnnotationPresent(Table.class));

        Entity entityAnnotation = Consolidado.class.getAnnotation(Entity.class);
        assertNotNull(entityAnnotation);

        Table tableAnnotation = Consolidado.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("Consolidado", tableAnnotation.name());
    }

    @Test
    void testIdAnnotation() throws Exception {
        assertTrue(Consolidado.class.getDeclaredField("data").isAnnotationPresent(Id.class));

        Id idAnnotation = Consolidado.class.getDeclaredField("data").getDeclaredAnnotation(Id.class);
        assertNotNull(idAnnotation);
    }

    @Test
    void testColumnAnnotation() throws Exception {
        assertTrue(Consolidado.class.getDeclaredField("saldo").isAnnotationPresent(Column.class));

        Column columnAnnotation = Consolidado.class.getDeclaredField("saldo").getAnnotation(Column.class);
        assertNotNull(columnAnnotation);
        assertFalse(columnAnnotation.nullable());
        assertEquals(17, columnAnnotation.precision());
        assertEquals(2, columnAnnotation.scale());
    }
}
