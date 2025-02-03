package com.pix.application.usecases.chave;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.infra.controller.chave.ChavePixRequest;
import com.pix.infra.controller.chave.ChavePixResponse;
import com.pix.infra.gateways.repository.ChavePixServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarChavePix {

    @Autowired
    ChavePixServiceRepository chavePixServiceRepository;

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    public ChavePixResponse executar(ChavePixRequest chavePixCadastrarRequest) {
        ChavePix chavePix = chavePixServiceRepository.salvar(chavePixFactory.criarChavePix(
                chavePixCadastrarRequest.chave(), chavePixCadastrarRequest.tipoChave()));
        return new ChavePixResponse(
                chavePix.getUuid(),
                chavePix.getChave(),
                chavePix.getTipo().name()
        );
    }
}
