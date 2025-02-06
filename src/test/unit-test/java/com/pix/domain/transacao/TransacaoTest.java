package com.pix.domain.transacao;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransacaoTest {



    @Test
    void deveRegistrarTransacaoComChavePixEmail() {
        ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePixOrigem = chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL);
        ChavePix chavePixDestino = chavePixFactory.criarChavePix("gil@gmail.com", TipoChavePix.EMAIL);
        Transacao transacao = Transacao.registrar(
                chavePixOrigem,
                new BigDecimal("1500.00"),
                chavePixDestino
        );
        assertEquals(new BigDecimal("1500.00"), transacao.getValorTransacao());
        assertEquals("frt@gmail.com", transacao.getChavePixOrigem());
        assertEquals(TipoChavePix.EMAIL, transacao.getTipoChavePixOrigem());
        assertEquals("gil@gmail.com", transacao.getChavePixDestino());
        assertEquals(TipoChavePix.EMAIL, transacao.getTipoChavePixDestino());
    }

    @Test
    void deveLancarExcecaoChavesIguais() {

    }



    @Test
    void getUuid() {
    }

    @Test
    void getValorTransacao() {
    }

    @Test
    void getChavePixOrigem() {
    }

    @Test
    void getChavePixDestino() {
    }

    @Test
    void getDataHora() {
    }
}