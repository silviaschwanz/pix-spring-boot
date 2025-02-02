package com.pix.infra.gateways;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.gateways.repository.ChavePixServiceRepository;
import com.pix.infra.gateways.repository.TransacaoServiceRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class TransacaoServiceRepositoryIT {

    @Autowired
    Flyway flyway;

    @Autowired
    TransacaoServiceRepository transacaoServiceRepository;

    @Autowired
    ChavePixServiceRepository chavePixServiceRepository;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Transactional
    @DisplayName("Deve registrar uma transação que esteja de acordo com as regras de validação")
    void registrar() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePixOrigem = chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL);
        ChavePix chavePixDestino = chavePixFactory.criarChavePix("gil@gmail.com", TipoChavePix.EMAIL);
        chavePixServiceRepository.salvar(chavePixOrigem);
        chavePixServiceRepository.salvar(chavePixDestino);

        Transacao transacao = Transacao.registrar(
                chavePixOrigem,
                new BigDecimal("1500.00"),
                chavePixDestino
        );
        Transacao transacaoRegistrada = transacaoServiceRepository.registrar(transacao);
        assertNotNull(transacaoRegistrada);
    }

    @Test
    @Transactional
    @DisplayName("Não deve registrar uma transação que contenha uma chave pix origem inexistente")
    void naoRegistrarComChavePixOrigemInexistente() {

    }

    @Test
    void buscarPorId() {
    }

    @Test
    void buscarTodas() {
    }
}