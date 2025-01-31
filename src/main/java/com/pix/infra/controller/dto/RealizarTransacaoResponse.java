package com.pix.infra.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RealizarTransacaoResponse(
        UUID uuid,
        BigDecimal valorTransacao,
        String chavePixOrigem,
        String chavePixDestino
) {
}
