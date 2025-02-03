package com.pix.infra.controller.transacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoResponse(
        UUID uuid,
        String chavePixOrigem,
        String chavePixDestino,
        String valor,
        LocalDateTime dataHora
) {
}
