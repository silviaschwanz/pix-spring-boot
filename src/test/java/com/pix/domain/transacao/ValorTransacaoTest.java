package com.pix.domain.transacao;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValorTransacaoTest {

    @Test
    void deveCriarValorTransacaoValido() {
        ValorTransacao valorTransacao = new ValorTransacao(BigDecimal.valueOf(2500.00));
        assertEquals(0, valorTransacao.valor().compareTo(BigDecimal.valueOf(2500.00)));
    }

    @Test
    void deveCriarValorTransacaoComDuasCasasDecimais() {
        ValorTransacao valorTransacao = new ValorTransacao(new BigDecimal("2500.456"));
        assertEquals(new BigDecimal("2500.46"), valorTransacao.valor());
    }

    @Test
    void deveLancarExcecaoParaValorNulo() {
        Exception exception = assertThrows(NullPointerException.class, () -> new ValorTransacao(null));
        assertEquals("Valor da transação é obrigatório.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaValorMenorQueMinimo() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ValorTransacao(new BigDecimal("0.001")));
        assertEquals("O valor da transação deve ser no mínimo R$0,01.", exception.getMessage());
    }

    @Test
    void deveAceitarValorIgualAoMinimo() {
        ValorTransacao valorTransacao = new ValorTransacao(new BigDecimal("0.01"));
        assertEquals(new BigDecimal("0.01"), valorTransacao.valor());
    }

    @Test
    void deveExibirValorFormatado() {
        ValorTransacao valorTransacao = new ValorTransacao(new BigDecimal("1234.5"));
        assertEquals("R$ 1234.50", valorTransacao.toString());
    }

}