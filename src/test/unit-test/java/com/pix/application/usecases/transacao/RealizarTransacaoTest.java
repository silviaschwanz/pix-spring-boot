package com.pix.application.usecases.transacao;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import com.pix.domain.transacao.Transacao;
import com.pix.infra.controller.chave.ChavePixRequest;
import com.pix.infra.controller.transacao.TransacaoRequest;
import com.pix.infra.controller.transacao.TransacaoResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

        TransacaoRequest realizarTransacaoRequest = new TransacaoRequest(
                new ChavePixRequest("frt@gmail.com", TipoChavePix.EMAIL),
                new ChavePixRequest("gil@gmail.com", TipoChavePix.EMAIL),
                new BigDecimal("4000.00")
        );
        TransacaoResponse transacaoResponse = realizarTransacao.executar(realizarTransacaoRequest);
        assertEquals("4000.00", transacaoResponse.valor());
        assertEquals(transacao.getUuid(), transacaoResponse.uuid());
        assertEquals(transacao.getChavePixOrigem(), transacaoResponse.chavePixOrigem());
        assertEquals(transacao.getChavePixDestino(), transacaoResponse.chavePixDestino());
    }

    @Test
    @DisplayName("Quando receber chave pix origem que não esteja cadastrada deve retornar exceção EntityNotFoundException")
    void lancarExcecaoQuandoNaoEncontrarChavePixOrigem() {
        String messageError = "Chave Pix não encontrada: frt@gmail.com";
        doThrow(new EntityNotFoundException("Chave Pix não encontrada: frt@gmail.com"))
                .when(chavePixServiceRepository).buscarPorChave("frt@gmail.com");
        TransacaoRequest realizarTransacaoRequest = new TransacaoRequest(
                new ChavePixRequest("frt@gmail.com", TipoChavePix.EMAIL),
                new ChavePixRequest("gil@gmail.com", TipoChavePix.EMAIL),
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
        TransacaoRequest realizarTransacaoRequest = new TransacaoRequest(
                new ChavePixRequest("frt@gmail.com", TipoChavePix.EMAIL),
                new ChavePixRequest("gil@gmail.com", TipoChavePix.EMAIL),
                new BigDecimal("4000.00")
        );
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            realizarTransacao.executar(realizarTransacaoRequest);;
        });
        assertEquals(messageError, exception.getMessage());
    }

}