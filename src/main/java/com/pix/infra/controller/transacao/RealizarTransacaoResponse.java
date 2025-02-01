package com.pix.infra.controller.transacao;

import com.pix.domain.transacao.ValorTransacao;

import java.util.UUID;

public record RealizarTransacaoResponse(
        UUID uuid,
        String valorTransacao,
        String chavePixOrigem,
        String chavePixDestino
) {
}