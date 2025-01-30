package com.pix.domain.transacao;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;

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
    private ChavePixFactory chavePixFactory;

    private static final BigDecimal LIMITE_SUSPEITO = new BigDecimal("5000.00");
    private static final List<String> CONTAS_SUSPEITAS = List.of("12345678900", "98765432100"); // Exemplo de chaves CPFs bloqueadas


    private Transacao(String chavePixOrigem, BigDecimal valor, String chavePixDestino) {
        compararChavesOrigemDestino();
        verificarSaldo();
        verificarLimiteDiario();
        verificarFraude();
        this.chavePixFactory = new ChavePixFactoryImpl();
        this.chavePixOrigem = interpretarChave(chavePixOrigem);
        this.chavePixDestino = interpretarChave(chavePixDestino);
        this.valorTransacao = new ValorTransacao(valor);
    }

    public static Transacao registrar(String chavePixOrigem, BigDecimal valor, String chavePixDestino) {
        return new Transacao(chavePixOrigem, valor, chavePixDestino);
    }

    private ChavePix interpretarChave(String valorChave) {
        return chavePixFactory.criarChavePix(valorChave);
    }

    private void compararChavesOrigemDestino() {
        if(Objects.equals(chavePixOrigem, chavePixDestino)) {
            throw new IllegalArgumentException("As chaves origem e destino não podem ser iguais.");
        }
    }

    private void verificarSaldo() {
        BigDecimal saldo = BigDecimal.valueOf(5000.00);
        if(valorTransacao.valor().compareTo(saldo) < 0 ) {
            throw new IllegalArgumentException("O valor da transação é superior ao valor do saldo.");
        }
    }

    private void verificarLimiteDiario() {
        BigDecimal limiteDiario = BigDecimal.valueOf(1000.00);
        if(valorTransacao.valor().compareTo(limiteDiario) < 0 ) {
            throw new IllegalArgumentException("O valor da transação é superior ao valor do limite diário permitido.");
        }
    }

    private void verificarFraude() {
        if (valorTransacao.valor().compareTo(LIMITE_SUSPEITO) > 0) {
            throw new IllegalArgumentException("Alerta de fraude: Transação com valor muito alto.");
        }
        if (CONTAS_SUSPEITAS.contains(chavePixDestino.valorChave())) {
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
        return chavePixOrigem.valorChave();
    }

    public String getChavePixDestino() {
        return chavePixDestino.valorChave();
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
