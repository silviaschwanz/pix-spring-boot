package com.pix.infra.persistence.transacao;

import com.pix.infra.persistence.chave.ChavePixEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransacaoJpaRepository extends JpaRepository<TransacaoEntity, Long> {

    Page<TransacaoEntity> findAllByChavePixOrigem(ChavePixEntity chavePixOrigem, Pageable paginacao);

    Optional<TransacaoEntity> findByUuid(UUID uuid);

}
