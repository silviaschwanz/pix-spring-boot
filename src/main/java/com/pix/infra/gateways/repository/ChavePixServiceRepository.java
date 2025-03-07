package com.pix.infra.gateways.repository;

import com.pix.application.gateways.ChavePixRepository;
import com.pix.domain.chave.ChavePix;
import com.pix.infra.gateways.repository.mapper.ChavePixMapper;
import com.pix.infra.persistence.chave.ChavePixEntity;
import com.pix.infra.persistence.chave.ChavePixJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChavePixServiceRepository implements ChavePixRepository {

    @Autowired
    ChavePixJpaRepository chavePixJpaRepository;

    @Autowired
    ChavePixMapper chavePixMapper;

    @Transactional
    public ChavePix salvar(ChavePix chavePix) {
        ChavePixEntity chavePixEntity = chavePixJpaRepository.save(chavePixMapper.toEntity(chavePix));
        return chavePixMapper.toDomain(chavePixEntity);
    }

    public ChavePix buscarPorChave(String chave) {
        ChavePixEntity chavePixEntity =  chavePixJpaRepository.findByChave(chave)
                .orElseThrow(() -> new EntityNotFoundException("Chave Pix não encontrada: " + chave));
        return chavePixMapper.toDomain(chavePixEntity);
    }

}
