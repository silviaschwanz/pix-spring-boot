package com.pix.application.usecases;

import com.pix.application.gateways.TransacaoRepository;
import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.controller.dto.RealizarTransacaoRequest;
import com.pix.infra.controller.dto.RealizarTransacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealizarTransacao {

    @Autowired
    TransacaoRepository transacaoRepository;

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    public RealizarTransacaoResponse executar(RealizarTransacaoRequest transacaoRequest) {
        ChavePix chavePixOrigem = chavePixFactory.criarChavePix(
                transacaoRequest.chavePixOrigem().chave(), transacaoRequest.chavePixOrigem().tipoChave()
        );
        ChavePix chavePixDestino = chavePixFactory.criarChavePix(
                transacaoRequest.chavePixDestino().chave(), transacaoRequest.chavePixDestino().tipoChave()
        );
        Transacao transacaoSolicitada = Transacao.registrar(
                chavePixOrigem,
                transacaoRequest.valor(),
                chavePixDestino
        );
        Transacao transacaoRealizada = transacaoRepository.registrar(transacaoSolicitada);
        return new RealizarTransacaoResponse(
                transacaoRealizada.getUuid(),
                transacaoRealizada.getValorTransacao(),
                transacaoRealizada.getChavePixOrigem(),
                transacaoRealizada.getChavePixDestino()
                );
    }
}
