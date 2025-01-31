package com.pix.domain.transacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record ValorTransacao(BigDecimal valor) {

    private static final BigDecimal VALOR_MINIMO = new BigDecimal("0.01");

    public ValorTransacao {
        Objects.requireNonNull(valor, "Valor da transação é obrigatório.");
        validar(valor);
        valor = arredondar(valor);
    }

    private void validar(BigDecimal valor) {
        if(valor.compareTo(VALOR_MINIMO) < 0) {
            throw new IllegalArgumentException("O valor da transação deve ser no mínimo R$0,01.");
        }
    }

    private BigDecimal arredondar(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "R$" + valor;
    }

}
