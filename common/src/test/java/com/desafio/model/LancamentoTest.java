package com.desafio.model;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class LancamentoTest {

    @Test
    void testGetterAndSetter() {
        Lancamento lancamento = new Lancamento();

        long id = 1L;
        LocalDateTime data = LocalDateTime.of(2023, 10, 13, 0, 0, 0);
        String descricao = "Test Lancamento";
        BigDecimal valor = new BigDecimal("100.50");
        String tipo = "Crédito";
        lancamento.setId(id);
        lancamento.setData(LocalDateTime.of(2023, 10, 13, 0, 0, 0));
        lancamento.setDescricao(descricao);
        lancamento.setValor(valor);
        lancamento.setTipo(tipo);

        assertEquals(id, lancamento.getId());
        assertEquals(data, lancamento.getData());
        assertEquals(descricao, lancamento.getDescricao());
        assertEquals(valor, lancamento.getValor());
        assertEquals(tipo, lancamento.getTipo());
    }

    @Test
    void testEquals() {
        // Duas instâncias com mesmos valores
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setData(LocalDateTime.now());
        lancamento1.setDescricao("Test Lancamento");
        lancamento1.setValor(new BigDecimal("100.50"));
        lancamento1.setTipo("Crédito");

        Lancamento lancamento2 = new Lancamento();
        lancamento2.setData(LocalDateTime.now());
        lancamento2.setDescricao("Test Lancamento");
        lancamento2.setValor(new BigDecimal("100.50"));
        lancamento2.setTipo("Crédito");

        // Verifica se são iguais
        assertEquals(lancamento1, lancamento2);
    }

    @Test
    void testNotEquals() {
        // Duas instâncias com valores diferentes
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setData(LocalDateTime.now());
        lancamento1.setDescricao("Test Lancamento");
        lancamento1.setValor(new BigDecimal("100.50"));
        lancamento1.setTipo("Crédito");

        Lancamento lancamento2 = new Lancamento();
        lancamento2.setData(LocalDateTime.now());
        lancamento2.setDescricao("Outro Test Lancamento");
        lancamento2.setValor(new BigDecimal("200.75"));
        lancamento2.setTipo("Débito");

        // Verificase são diferentes
        assertNotEquals(lancamento1, lancamento2);
    }

@Test
    void testAnnotations() {
        // Check if the annotations are present
        assertTrue(Lancamento.class.isAnnotationPresent(Entity.class));
        assertTrue(Lancamento.class.isAnnotationPresent(Table.class));

        // Check the values of the annotations
        Entity entityAnnotation = Lancamento.class.getAnnotation(Entity.class);
        assertNotNull(entityAnnotation);

        Table tableAnnotation = Lancamento.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("Lancamento", tableAnnotation.name());
    }

    @Test
    void testIdAnnotation() throws Exception {
        // Check if the @Id annotation is present
        assertTrue(Lancamento.class.getDeclaredField("id").isAnnotationPresent(Id.class));
        assertTrue(Lancamento.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class));

        // Verify the @Id annotation
        Id idAnnotation = Lancamento.class.getDeclaredField("id").getAnnotation(Id.class);
        assertNotNull(idAnnotation);

        // Verify the @GeneratedValue annotation
        GeneratedValue generatedValueAnnotation = Lancamento.class.getDeclaredField("id").getAnnotation(GeneratedValue.class);
        assertNotNull(generatedValueAnnotation);
        assertEquals(GenerationType.IDENTITY, generatedValueAnnotation.strategy());
    }

    @Test
    void testDataColumnAnnotation() throws NoSuchFieldException, SecurityException {
        // Check if the @Column annotation is present for the 'data' field
        assertTrue(Lancamento.class.getDeclaredField("data").isAnnotationPresent(Column.class));

        // Verify the @Column annotation for 'data' field
        Column columnAnnotation = Lancamento.class.getDeclaredField("data").getAnnotation(Column.class);
        assertNotNull(columnAnnotation);
        assertFalse(columnAnnotation.nullable());
    }

    @Test
    void testDescricaoColumnAnnotation() throws NoSuchFieldException, SecurityException {
        // Check if the @Column annotation is present for the 'descricao' field
        assertTrue(Lancamento.class.getDeclaredField("descricao").isAnnotationPresent(Column.class));

        // Verify the @Column annotation for 'descricao' field
        Column columnAnnotation = Lancamento.class.getDeclaredField("descricao").getAnnotation(Column.class);
        assertNotNull(columnAnnotation);
        assertFalse(columnAnnotation.nullable());
        assertEquals(100, columnAnnotation.length());
    }

    @Test
    void testValorColumnAnnotation() throws NoSuchFieldException, SecurityException {
        // Check if the @Column annotation is present for the 'valor' field
        assertTrue(Lancamento.class.getDeclaredField("valor").isAnnotationPresent(Column.class));

        // Verify the @Column annotation for 'valor' field
        Column columnAnnotation = Lancamento.class.getDeclaredField("valor").getAnnotation(Column.class);
        assertNotNull(columnAnnotation);
        assertFalse(columnAnnotation.nullable());
        assertEquals(17, columnAnnotation.precision());
        assertEquals(2, columnAnnotation.scale());
    }

    @Test
    void testTipoColumnAnnotation() throws NoSuchFieldException, SecurityException {
        // Check if the @Column annotation is present for the 'tipo' field
        assertTrue(Lancamento.class.getDeclaredField("tipo").isAnnotationPresent(Column.class));

        // Verify the @Column annotation for 'tipo' field
        Column columnAnnotation = Lancamento.class.getDeclaredField("tipo").getAnnotation(Column.class);
        assertNotNull(columnAnnotation);
        assertFalse(columnAnnotation.nullable());
        assertEquals(10, columnAnnotation.length());
    }
}
