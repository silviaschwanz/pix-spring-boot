package com.pix.infra.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record RealizarTransacaoRequest(
        @Valid
        DadosChavePix chavePixOrigem,

        @Valid
        DadosChavePix chavePixDestino,

        @NotNull(message = "O valor da transação pix não pode ser nulo.")
        @DecimalMin(value = "0.01", message = "O valor mínimo para uma transação pix é R$ 0,01.")
        BigDecimal valor
) {
}
