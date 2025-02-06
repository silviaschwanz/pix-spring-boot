package com.pix.infra.persistence.transacao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransacaoJpaRepository extends JpaRepository<TransacaoEntity, Long> {

    Optional<TransacaoEntity> findByUuid(UUID uuid);

    @Query("""
       SELECT t.uuid AS uuidTransacao,
              origem.chavePix AS chavePixOrigem,
              destino.chavePix AS chavePixDestino,
              t.valor AS valor,
              t.dataHora AS dataHora
       FROM TransacaoEntity t
       JOIN t.transacaoChaves origem ON origem.tipoLigacao = 'ORIGEM'
       JOIN t.transacaoChaves destino ON destino.tipoLigacao = 'DESTINO'
       WHERE origem.chavePix.chave = :chaveOrigem
       """)
    Page<TransacaoChavePixOrigemProjection> findByChaveOrigem(@Param("chaveOrigem") String chaveOrigem, Pageable paginacao);
}
