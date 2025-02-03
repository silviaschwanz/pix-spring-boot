package com.pix.infra.controller.transacao;

import java.util.Set;

public record ListarTransacaoResponse(
        Set<DadosTransacao> transacoes
) {
}
