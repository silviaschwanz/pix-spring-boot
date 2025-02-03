package com.pix.application.usecases.transacao;

import com.pix.application.gateways.TransacaoRepository;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.controller.transacao.DadosChavePix;
import com.pix.infra.controller.transacao.DadosTransacao;
import com.pix.infra.controller.transacao.ListarTransacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ListarTransacoesEnviadasChaveOrigem {

    @Autowired
    TransacaoRepository transacaoRepository;

    public ListarTransacaoResponse executar(String chave, Pageable paginacao) {
        Set<Transacao> transacoesDaChave = transacaoRepository.buscarChaveOrigem(chave, paginacao);
        Set<DadosTransacao> transacaos = transacoesDaChave
                .stream()
                .map((Transacao t) -> new DadosTransacao(
                        t.getUuid(),
                        new DadosChavePix(
                                t.getChavePixOrigem(),
                                t.getTipoChavePixOrigem()
                        ),
                        new DadosChavePix(
                                t.getChavePixDestino(),
                                t.getTipoChavePixDestino()
                        ),
                        t.getValorTransacao(),
                        t.getDataHora()
                ))
                .collect(Collectors.toSet());
        return new ListarTransacaoResponse(transacaos);
    }
}
