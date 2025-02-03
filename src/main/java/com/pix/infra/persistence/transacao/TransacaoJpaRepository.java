package com.pix.infra.persistence.transacao;

import com.pix.infra.persistence.chave.ChavePixEntity;
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

    @Query(
            "SELECT t FROM TransacaoEntity t " +
                    "JOIN t.chaves origem ON origem.tipoLigacao = 'ORIGEM' " +
                    "JOIN t.chaves destino ON destino.tipoLigacao = 'DESTINO' " +
                    "WHERE origem.chavePix.chave = :chaveOrigem " +
                    "AND destino.chavePix.chave = :chaveDestino"
    )
    Page<TransacaoEntity> findByOrigemAndDestino(
            @Param("chaveOrigem") String chaveOrigem,
            @Param("chaveDestino") String chaveDestino,
            Pageable paginacao
    );

    @Query(
            "SELECT t FROM TransacaoEntity t " +
                    "JOIN t.chaves origem ON origem.tipoLigacao = 'ORIGEM' " +
                    "WHERE origem.chavePix.chave = :chaveOrigem"
    )
    Page<TransacaoEntity> findByChaveOrigem(@Param("chaveOrigem") String chaveOrigem, Pageable paginacao);

}
