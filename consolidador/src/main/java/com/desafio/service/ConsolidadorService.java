package com.desafio.service;

import com.desafio.model.ConsolidacaoLancamentos;
import com.desafio.model.Consolidado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ConsolidadorService {
    private static Logger LOG = LoggerFactory.getLogger(ConsolidadorService.class);

    @Autowired
    private ConsolidadoService consolidadoService;
    @Autowired
    private LancamentoService lancamentoService;
    /**
     * Recupera a última consolidação anterior à data passada no parâmetro (inicio)
     *  e consolida todos os dias desde essa data até o dia anterior à execução.
     *  Exemplo: parâmetro = 12/10, última consolidação antes de 12/10 foi em 08/10
     *  e o dia da execução é 30/10. O batch vai recuperar os valores consolidados em 08/10,
     *  calcular a consolidação dos dias 12/10 até 29/10.
     *  Neste caso, mesmo que existam valores consolidados entre os dias 12/10 a 29/10,
     *  os valores serão sobreescritos.
     * @param inicio data inicial da consolidação
     */
    public void consolidaAPartirDe(LocalDate inicio) {
		LocalDate ontem = LocalDate.now();
		ontem = ontem.minusDays(1);
        // recalcula tudo a partir da data informada até ontem
        Consolidado anterior = consolidadoService.getLastConsolidadoUntilData(inicio);
        consolidaIntervalo(inicio, ontem, anterior == null ? BigDecimal.ZERO : anterior.getSaldo());
    }
    /**
     * O agendamento diário executa desta forma.
     *  Neste modo de execução recupera o último valor consolidado e calcula o dia seguinte
     *  e depois o seguinte até chegar no dia anterior à execução.
     *  Exemplo, se a última consolidação é do dia 12/10 e é executado no dia 30/10,
     *  então será feita a consolidação dos dias 13/10 até 29/10.
     *  Neste modo se a última consolidação foi no dia anterior, nada é executado.
     */
    public void consolida() {
        // sem parâmetro, usa a data do ultimo saldo e neste caso, não recalcula
		LocalDate ontem = LocalDate.now();
		ontem = ontem.minusDays(1);
        Consolidado anterior = consolidadoService.getLastConsolidado();
        if (anterior == null) {
            // como não tem nenhum consolidado, calcula desde o início
            LocalDate inicio = lancamentoService.obterPrimeiraData().toLocalDate();
            consolidaIntervalo(inicio, ontem, BigDecimal.ZERO);
        } else if (anterior.getData().isBefore(ontem)) { // não calcula saldo se já tiver saldo postorior
             // calcular a partir da data do último consolidado
            LocalDate inicio = anterior.getData().plusDays(1);
            BigDecimal saldoAnterior = anterior == null ? BigDecimal.ZERO : anterior.getSaldo();
            consolidaIntervalo(inicio, ontem, saldoAnterior);
        } else {
            LOG.info("Já existe um consolidado para a data {} ou após essa data", ontem.toString());
        }
    }
    /**
     * Executa a consolidação de um intervalo de dias e utiliza o parametro saldo como 
     * valor inicial.
     * @param inicio
     * @param fim
     * @param saldo
     */
    public void consolidaIntervalo(LocalDate inicio, LocalDate fim, BigDecimal saldo) {
        for(LocalDate data = inicio; data.isBefore(fim) || data.isEqual(fim); data = data.plusDays(1)) {
            saldo = consolidaDia(data, saldo);
        }
    }
    /**
     * Executa a consolidação de um dia e utiliza o parametro saldo como 
     * valor inicial.
     * @param data a data a ser consolidada
     * @param saldo o saldo anterior
     * @return o saldo consolidado
     */
	public BigDecimal consolidaDia(LocalDate data, BigDecimal saldo) {
		ConsolidacaoLancamentos deb = lancamentoService.calcularConsolidado(data, "débito");
		ConsolidacaoLancamentos cred = lancamentoService.calcularConsolidado(data, "crédito");
        BigDecimal totalDebito = deb==null?null:deb.getValorTotal();
        if (totalDebito == null) totalDebito = BigDecimal.ZERO;
        BigDecimal totalCredito = cred==null?null:cred.getValorTotal();
        if (totalCredito == null) totalCredito = BigDecimal.ZERO;
		saldo = saldo.subtract(totalDebito);
		saldo = saldo.add(totalCredito);
        long qtdCredito = cred==null?0:cred.getQuantidade() == null ? 0 : cred.getQuantidade();
        long qtdDebito = deb==null?0:deb.getQuantidade() == null ? 0 : deb.getQuantidade(); 
		Consolidado consolidado = new Consolidado(data, saldo, totalDebito, totalCredito, qtdDebito, qtdCredito);
		consolidadoService.saveConsolidado(consolidado);
		LOG.info("Consolidado atualizado para {}. Crédito: {}; Débito: {}; Saldo: {}", data.toString(), totalCredito.toString(), totalDebito.toString(), saldo.toString());
		return saldo;
	}
}
