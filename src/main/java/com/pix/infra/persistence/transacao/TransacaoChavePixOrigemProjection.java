package com.pix.infra.persistence.transacao;

import com.pix.infra.persistence.chave.ChavePixEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface TransacaoChavePixOrigemProjection {
    UUID getUuidTransacao();
    ChavePixEntity getChavePixOrigem();
    ChavePixEntity getChavePixDestino();
    BigDecimal getValor();
    LocalDateTime getDataHora();
}
