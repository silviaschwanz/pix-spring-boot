package com.pix.application.usecases.transacao;

import com.pix.application.gateways.TransacaoRepository;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.controller.transacao.TransacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ListarTransacoesEnviadasChaveOrigem {

    @Autowired
    TransacaoRepository transacaoRepository;

    public Set<TransacaoResponse> executar(String chave, Pageable paginacao) {
        Set<Transacao> transacoesDaChave = transacaoRepository.buscarChaveOrigem(chave, paginacao);
        return transacoesDaChave
                .stream()
                .map((Transacao t) -> new TransacaoResponse(
                        t.getUuid(),
                        t.getChavePixOrigem(),
                        t.getChavePixDestino(),
                        t.getValorTransacao().toString(),
                        t.getDataHora()
                ))
                .collect(Collectors.toSet());
    }
}
