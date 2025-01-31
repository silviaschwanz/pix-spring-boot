package com.pix.application.gateways;

import com.pix.domain.transacao.Transacao;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface TransacaoRepository {

    Transacao registrar(Transacao transacao) throws EntityExistsException;

    Transacao buscarPorId(UUID id) throws EntityNotFoundException;

    Set<Transacao> buscarTodas() throws EntityNotFoundException;

}
