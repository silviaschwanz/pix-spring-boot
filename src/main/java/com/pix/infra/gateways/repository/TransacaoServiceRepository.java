package com.pix.infra.gateways.repository;

import com.pix.application.gateways.TransacaoRepository;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.gateways.repository.mapper.TransacaoMapper;
import com.pix.infra.persistence.chave.ChavePixEntity;
import com.pix.infra.persistence.chave.ChavePixJpaRepository;
import com.pix.infra.persistence.transacao.TransacaoEntity;
import com.pix.infra.persistence.transacao.TransacaoJpaRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Repository
public class TransacaoServiceRepository implements TransacaoRepository {

    @Autowired
    TransacaoJpaRepository transacaoJpaRepository;

    @Autowired
    ChavePixJpaRepository chavePixJpaRepository;

    @Autowired
    TransacaoMapper transacaoMapper;

    @Transactional
    @Override
    public Transacao registrar(Transacao transacao) throws EntityExistsException {
        ChavePixEntity chavePixEntityOrigem = buscarChavePix(transacao.getChavePixOrigem());
        ChavePixEntity chavePixEntityDestino = buscarChavePix(transacao.getChavePixDestino());
        TransacaoEntity transacaoEntity = transacaoJpaRepository.save(
                transacaoMapper.toEntity(transacao, chavePixEntityOrigem, chavePixEntityDestino)
        );
        return transacaoMapper.toDomain(transacaoEntity);
    }

    @Override
    public Transacao buscarPorId(UUID uuid) throws EntityNotFoundException {
        TransacaoEntity transacaoEntity =  transacaoJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada: " + uuid));
        return transacaoMapper.toDomain(transacaoEntity);
    }

    @Override
    public Set<Transacao> buscarTodas() throws EntityNotFoundException {
        return Set.of();
    }

    private ChavePixEntity buscarChavePix(String chave) {
        return chavePixJpaRepository.findByChave(chave)
                .orElseThrow(() -> new EntityNotFoundException("Chave Pix não encontrada: " + chave));
    }

}
