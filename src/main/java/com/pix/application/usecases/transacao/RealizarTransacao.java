package com.pix.application.usecases.transacao;

import com.pix.application.gateways.TransacaoRepository;
import com.pix.domain.chave.ChavePix;
import com.pix.domain.transacao.Transacao;
import com.pix.domain.transacao.ValorTransacao;
import com.pix.infra.controller.transacao.RealizarTransacaoRequest;
import com.pix.infra.controller.transacao.RealizarTransacaoResponse;
import com.pix.infra.gateways.repository.ChavePixServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealizarTransacao {

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    ChavePixServiceRepository chavePixServiceRepository;

    public RealizarTransacaoResponse executar(RealizarTransacaoRequest transacaoRequest) {
        ChavePix chavePixOrigem = chavePixServiceRepository.buscarPorChave(transacaoRequest.chavePixOrigem().chave());
        ChavePix chavePixDestino = chavePixServiceRepository.buscarPorChave(transacaoRequest.chavePixDestino().chave());
        Transacao transacaoSolicitada = Transacao.registrar(
                chavePixOrigem,
                transacaoRequest.valor(),
                chavePixDestino
        );
        Transacao transacaoRealizada = transacaoRepository.registrar(transacaoSolicitada);
        return new RealizarTransacaoResponse(
                transacaoRealizada.getUuid(),
                transacaoRealizada.getValorTransacao().toString(),
                transacaoRealizada.getChavePixOrigem(),
                transacaoRealizada.getChavePixDestino()
        );
    }
}
