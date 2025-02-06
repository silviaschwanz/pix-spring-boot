package com.pix.infra.gateways.repository;

import com.pix.application.gateways.TransacaoRepository;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.gateways.repository.mapper.TransacaoMapper;
import com.pix.infra.persistence.chave.ChavePixEntity;
import com.pix.infra.persistence.chave.ChavePixJpaRepository;
import com.pix.infra.persistence.transacao.TransacaoChavePixOrigemProjection;
import com.pix.infra.persistence.transacao.TransacaoEntity;
import com.pix.infra.persistence.transacao.TransacaoJpaRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada: " + uuid));
        return transacaoMapper.toDomain(transacaoEntity);
    }

    @Override
    public Set<Transacao> buscarPorChaveOrigem(String chaveOrigem, Pageable paginacao) throws EntityNotFoundException {
        Page<TransacaoChavePixOrigemProjection> transacoesPage = transacaoJpaRepository.findByChaveOrigem(chaveOrigem, paginacao);
        if (transacoesPage.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma transação encontrada para a chave de origem: " + chaveOrigem);
        }
        return transacoesPage.stream()
                .map(transacaoMapper::toDomain)
                .collect(Collectors.toSet());
    }

    private ChavePixEntity buscarChavePix(String chave) {
        return chavePixJpaRepository.findByChave(chave)
                .orElseThrow(() -> new EntityNotFoundException("Chave Pix não encontrada: " + chave));
    }

}
