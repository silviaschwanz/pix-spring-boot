package com.pix.infra.gateways.repository.mapper;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.persistence.chave.ChavePixEntity;
import com.pix.infra.persistence.transacao.TransacaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoMapper {

    @Autowired
    ChavePixMapper chavePixMapper;

    public Transacao toDomain(TransacaoEntity transacaoEntity) {
        ChavePix chavePixOrigem = chavePixMapper.toDomain(transacaoEntity.getChavePixOrigem());
        ChavePix chavePixDestino = chavePixMapper.toDomain(transacaoEntity.getChavePixDestino());
        return Transacao.restaurar(
                transacaoEntity.getUuid(),
                chavePixOrigem,
                transacaoEntity.getValor(),
                chavePixDestino,
                transacaoEntity.getDataHora()
        );
    }

    public TransacaoEntity toEntity(
            Transacao transacao,
            ChavePixEntity chavePixEntityOrigem,
            ChavePixEntity chavePixEntityDestino
    ) {
        return new TransacaoEntity(chavePixEntityOrigem, transacao.getValorTransacao(), chavePixEntityDestino);
    }
}
