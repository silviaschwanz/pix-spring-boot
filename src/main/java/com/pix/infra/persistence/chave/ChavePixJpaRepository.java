package com.pix.infra.persistence.chave;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChavePixJpaRepository extends JpaRepository<ChavePixEntity, Long> {

    Optional<ChavePixEntity> findByChave(String chave);

    Optional<ChavePixEntity> findByUuid(UUID uuid);

}
