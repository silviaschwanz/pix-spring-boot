package com.pix.domain.transacao;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.tipo.TipoChavePix;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Transacao {

    private UUID uuid;
    private ChavePix chavePixOrigem;
    private ValorTransacao valorTransacao;
    private ChavePix chavePixDestino;
    private LocalDateTime dataHora;

    private static final BigDecimal LIMITE_SUSPEITO = new BigDecimal("5000.00");
    private static final List<String> CONTAS_SUSPEITAS = List.of("12345678900", "98765432100"); // Exemplo de chaves CPFs bloqueadas


    private Transacao(ChavePix chavePixOrigem, BigDecimal valor, ChavePix chavePixDestino) {
        this.uuid = UUID.randomUUID();
        this.chavePixOrigem = chavePixOrigem;
        this.chavePixDestino = chavePixDestino;
        this.valorTransacao = new ValorTransacao(valor);
        this.dataHora = LocalDateTime.now();
    }

    private Transacao(
            UUID uuid,
            ChavePix chavePixOrigem,
            ValorTransacao valorTransacao,
            ChavePix chavePixDestino,
            LocalDateTime dataHora
    ) {
        this.uuid = uuid;
        this.chavePixOrigem = chavePixOrigem;
        this.valorTransacao = valorTransacao;
        this.chavePixDestino = chavePixDestino;
        this.dataHora = dataHora;
    }

    public static Transacao registrar(ChavePix chavePixOrigem, BigDecimal valor, ChavePix chavePixDestino) {
        compararChavesOrigemDestino(chavePixOrigem, chavePixDestino);
        verificarSaldo(valor);
        verificarLimiteDiario(valor);
        verificarFraude(valor, chavePixDestino);
        return new Transacao(chavePixOrigem, valor, chavePixDestino);
    }

    public static Transacao restaurar(
            UUID uuid,
            ChavePix chavePixOrigem,
            BigDecimal valor,
            ChavePix chavePixDestino,
            LocalDateTime dataHora
    ) {
        ValorTransacao valorTransacao = new ValorTransacao(valor);
        return new Transacao(uuid, chavePixOrigem, valorTransacao, chavePixDestino, dataHora);
    }

    private static void compararChavesOrigemDestino(ChavePix chavePixOrigem, ChavePix chavePixDestino) {
        if(Objects.equals(chavePixOrigem, chavePixDestino)) {
            throw new IllegalArgumentException("As chaves origem e destino não podem ser iguais.");
        }
    }

    private static void verificarSaldo(BigDecimal valor) {
        BigDecimal saldo = new BigDecimal("5000.00");
        if(valor.compareTo(saldo) > 0 ) {
            throw new IllegalArgumentException("O valor da transação é superior ao valor do saldo.");
        }
    }

    private static void verificarLimiteDiario(BigDecimal valor) {
        BigDecimal limiteDiario = BigDecimal.valueOf(1000.00);
        if(valor.compareTo(limiteDiario) < 0 ) {
            throw new IllegalArgumentException("O valor da transação é superior ao valor do limite diário permitido.");
        }
    }

    private static void verificarFraude(BigDecimal valor, ChavePix chavePixDestino) {
        if (valor.compareTo(LIMITE_SUSPEITO) > 0) {
            throw new IllegalArgumentException("Alerta de fraude: Transação com valor muito alto.");
        }
        if (CONTAS_SUSPEITAS.contains(chavePixDestino.getChave())) {
            throw new IllegalArgumentException("Alerta de fraude: Destinatário suspeito.");
        }
        //É possível implementar a verificação de transações recentes e/ou por minuto

    }

    public UUID getUuid() {
        return uuid;
    }

    public BigDecimal getValorTransacao() {
        return valorTransacao.valor();
    }

    public String getChavePixOrigem() {
        return chavePixOrigem.getChave();
    }

    public TipoChavePix getTipoChavePixOrigem() {
        return chavePixOrigem.getTipo();
    }

    public String getChavePixDestino() {
        return chavePixDestino.getChave();
    }

    public TipoChavePix getTipoChavePixDestino() {
        return chavePixDestino.getTipo();
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
