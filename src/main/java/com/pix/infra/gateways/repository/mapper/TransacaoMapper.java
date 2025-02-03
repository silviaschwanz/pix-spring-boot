package com.pix.infra.gateways.repository.mapper;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.transacao.TipoLigacao;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.persistence.TransacaoChavePixEntity;
import com.pix.infra.persistence.chave.ChavePixEntity;
import com.pix.infra.persistence.transacao.TransacaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoMapper {

    @Autowired
    ChavePixMapper chavePixMapper;

    public Transacao toDomain(TransacaoEntity transacaoEntity) {
        ChavePix chavePixOrigem = null;
        ChavePix chavePixDestino = null;
        for (TransacaoChavePixEntity transacaoChavePix : transacaoEntity.getChaves()) {
            if(TipoLigacao.ORIGEM.name().equals(transacaoChavePix.getTipoLigacao())) {
                chavePixOrigem = chavePixMapper.toDomain(transacaoChavePix.getChavePix());
            }
            if(TipoLigacao.DESTINO.name().equals(transacaoChavePix.getTipoLigacao())) {
                chavePixDestino = chavePixMapper.toDomain(transacaoChavePix.getChavePix());
            }
        }
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
        TransacaoEntity transacaoEntity = new TransacaoEntity(
                transacao.getUuid(),
                transacao.getValorTransacao(),
                transacao.getDataHora()
        );
        transacaoEntity.adicionarChave(chavePixEntityOrigem, TipoLigacao.ORIGEM.name());
        transacaoEntity.adicionarChave(chavePixEntityDestino, TipoLigacao.DESTINO.name());
        return transacaoEntity;
    }
}
