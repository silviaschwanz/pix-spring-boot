package com.pix.infra.gateways;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import com.pix.infra.gateways.repository.ChavePixServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ChavePixServiceRepositoryIT {

    @Autowired
    Flyway flyway;

    @Autowired
    ChavePixServiceRepository chavePixServiceRepository;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Deve salvar chave pix com um dos formatos válidos: cpf, cnpj, celular, email ou chave aleatória")
    void salvar() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePix = chavePixServiceRepository.salvar(
                chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL)
        );
        assertEquals("frt@gmail.com", chavePix.getChave());
        assertEquals(TipoChavePix.EMAIL, chavePix.getTipo());
        assertNotNull(chavePix.getUuid());
    }

    @Test
    @DisplayName("Deve retornar uma exceção IllegalArgumentException quando buscar por uma chave pix inexistente")
    void naoDeveSalvarChaveInvalida() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            chavePixServiceRepository.salvar(chavePixFactory.criarChavePix("frt@gmail.com??", TipoChavePix.EMAIL));
        });
        assertEquals("Chave Pix do tipo Email inválida.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma chave pix completa ao buscar por um valor de chave existente")
    void buscarPorChave() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        chavePixServiceRepository.salvar(
                chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL)
        );
        ChavePix chavePixEncontrada = chavePixServiceRepository.buscarPorChave("frt@gmail.com");
        assertEquals("frt@gmail.com", chavePixEncontrada.getChave());
        assertEquals(TipoChavePix.EMAIL, chavePixEncontrada.getTipo());
        assertNotNull(chavePixEncontrada.getUuid());
    }

    @Test
    @DisplayName("Deve retornar uma exceção EntityNotFoundException quando buscar por uma chave pix inexistente")
    void lancarExcecaoQuandoBuscarPorChaveInexistente() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            chavePixServiceRepository.buscarPorChave("frt@gmail.com");
        });
        assertEquals("Chave Pix não encontrada: frt@gmail.com", exception.getMessage());
    }

}