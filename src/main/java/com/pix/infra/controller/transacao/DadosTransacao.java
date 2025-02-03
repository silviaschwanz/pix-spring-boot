package com.pix.infra.controller.transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DadosTransacao(
        UUID uuid,
        DadosChavePix chavePixOrigem,
        DadosChavePix chavePixDestino,
        BigDecimal valor,
        LocalDateTime dataHora
) {
}
