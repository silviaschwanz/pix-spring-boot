package com.pix.application.usecases.chave;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import com.pix.infra.controller.chave.ChavePixRequest;
import com.pix.infra.controller.chave.ChavePixResponse;
import com.pix.infra.gateways.repository.ChavePixServiceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarChavePixTest {

    @Mock
    ChavePixServiceRepository chavePixServiceRepository;

    @InjectMocks
    CadastrarChavePix cadastrarChavePix;

    @Test
    @DisplayName("Cadastrar Chave Pix com dados válidos")
    void cadastrarChavePix() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePix = chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL);
        when(chavePixServiceRepository.salvar(any(ChavePix.class))).thenReturn(chavePix);
        ChavePixRequest request = new ChavePixRequest("frt@gmail.com", TipoChavePix.EMAIL);
        ChavePixResponse response = cadastrarChavePix.executar(request);
        assertEquals(chavePix.getUuid(), response.uuid());
        assertEquals(chavePix.getChave(), response.chave());
        assertEquals(chavePix.getTipo().name(), response.tipo());
    }

    @Test
    @DisplayName("Deve lançar exceção IllegalArgumentException ao tentar cadastrar uma chave pix do tipo Email com " +
            "formato de email inválido")
    void naoDeveCadastrarChavePixInvalida() {
        ChavePixRequest request = new ChavePixRequest("soul123@gmail.com?", TipoChavePix.EMAIL);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cadastrarChavePix.executar(request);
        });
        assertEquals("Chave Pix do tipo Email inválida.", exception.getMessage());
    }

}