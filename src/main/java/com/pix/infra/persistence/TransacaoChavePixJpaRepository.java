package com.pix.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoChavePixJpaRepository extends JpaRepository<TransacaoChavePixEntity, Long> {
}
