package com.pix.application.usecases.transacao;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.controller.transacao.DadosChavePix;
import com.pix.infra.controller.transacao.RealizarTransacaoRequest;
import com.pix.infra.controller.transacao.RealizarTransacaoResponse;
import com.pix.infra.gateways.repository.ChavePixServiceRepository;
import com.pix.infra.gateways.repository.TransacaoServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RealizarTransacaoTest {

    @Mock
    TransacaoServiceRepository transacaoServiceRepository;

    @Mock
    ChavePixServiceRepository chavePixServiceRepository;

    @InjectMocks
    RealizarTransacao realizarTransacao;

    @Test
    @DisplayName("Quando receber chaves pix já cadastradas deve executar o caso de uso Realizar Transação")
    void realizarTransacao() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePixOrigem = chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL);
        ChavePix chavePixDestino = chavePixFactory.criarChavePix("gil@gmail.com", TipoChavePix.EMAIL);
        when(chavePixServiceRepository.buscarPorChave(chavePixOrigem.getChave())).thenReturn(chavePixOrigem);
        when(chavePixServiceRepository.buscarPorChave(chavePixDestino.getChave())).thenReturn(chavePixDestino);

        Transacao transacao = Transacao.registrar(
                chavePixOrigem,
                new BigDecimal("4000.00"),
                chavePixDestino
        );
        when(transacaoServiceRepository.registrar(any(Transacao.class))).thenReturn(transacao);

        RealizarTransacaoRequest realizarTransacaoRequest = new RealizarTransacaoRequest(
                new DadosChavePix("frt@gmail.com", TipoChavePix.EMAIL),
                new DadosChavePix("gil@gmail.com", TipoChavePix.EMAIL),
                new BigDecimal("4000.00")
        );
        RealizarTransacaoResponse realizarTransacaoResponse = realizarTransacao.executar(realizarTransacaoRequest);
        assertEquals("4000.00", realizarTransacaoResponse.valorTransacao());
        assertEquals(transacao.getUuid(), realizarTransacaoResponse.uuid());
        assertEquals(transacao.getChavePixOrigem(), realizarTransacaoResponse.chavePixOrigem());
        assertEquals(transacao.getChavePixDestino(), realizarTransacaoResponse.chavePixDestino());
    }

    @Test
    @DisplayName("Quando receber chave pix origem que não esteja cadastrada deve retornar exceção EntityNotFoundException")
    void lancarExcecaoQuandoNaoEncontrarChavePixOrigem() {
        String messageError = "Chave Pix não encontrada: frt@gmail.com";
        doThrow(new EntityNotFoundException("Chave Pix não encontrada: frt@gmail.com"))
                .when(chavePixServiceRepository).buscarPorChave("frt@gmail.com");
        RealizarTransacaoRequest realizarTransacaoRequest = new RealizarTransacaoRequest(
                new DadosChavePix("frt@gmail.com", TipoChavePix.EMAIL),
                new DadosChavePix("gil@gmail.com", TipoChavePix.EMAIL),
                new BigDecimal("4000.00")
        );
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            realizarTransacao.executar(realizarTransacaoRequest);;
        });
        assertEquals(messageError, exception.getMessage());
    }

    @Test
    @DisplayName("Quando receber chave pix destino que não esteja cadastrada deve retornar exceção EntityNotFoundException")
    void lancarExcecaoQuandoNaoEncontrarChavePixDestino() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePixOrigem = chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL);
        when(chavePixServiceRepository.buscarPorChave(chavePixOrigem.getChave())).thenReturn(chavePixOrigem);
        String messageError = "Chave Pix não encontrada: gil@gmail.com";
        doThrow(new EntityNotFoundException(messageError))
                .when(chavePixServiceRepository).buscarPorChave("gil@gmail.com");
        RealizarTransacaoRequest realizarTransacaoRequest = new RealizarTransacaoRequest(
                new DadosChavePix("frt@gmail.com", TipoChavePix.EMAIL),
                new DadosChavePix("gil@gmail.com", TipoChavePix.EMAIL),
                new BigDecimal("4000.00")
        );
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            realizarTransacao.executar(realizarTransacaoRequest);;
        });
        assertEquals(messageError, exception.getMessage());
    }

}