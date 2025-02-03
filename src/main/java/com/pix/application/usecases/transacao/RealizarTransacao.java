package com.pix.application.usecases.transacao;

import com.pix.application.gateways.ChavePixRepository;
import com.pix.application.gateways.TransacaoRepository;
import com.pix.domain.chave.ChavePix;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.controller.transacao.TransacaoRequest;
import com.pix.infra.controller.transacao.TransacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealizarTransacao {

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    ChavePixRepository chavePixRepository;

    public TransacaoResponse executar(TransacaoRequest transacaoRequest) {
        ChavePix chavePixOrigem = chavePixRepository.buscarPorChave(transacaoRequest.chavePixOrigem().chave());
        ChavePix chavePixDestino = chavePixRepository.buscarPorChave(transacaoRequest.chavePixDestino().chave());
        Transacao transacaoSolicitada = Transacao.registrar(
                chavePixOrigem,
                transacaoRequest.valor(),
                chavePixDestino
        );
        Transacao transacaoRealizada = transacaoRepository.registrar(transacaoSolicitada);
        return new TransacaoResponse(
                transacaoRealizada.getUuid(),
                transacaoRealizada.getChavePixOrigem(),
                transacaoRealizada.getChavePixDestino(),
                transacaoRealizada.getValorTransacao().toString(),
                transacaoRealizada.getDataHora()
        );
    }
}
