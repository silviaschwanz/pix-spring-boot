package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CelularPixTest {

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    @ParameterizedTest
    @CsvSource({
            "11987654321",
            "(11)98765-4321",
            "(1198765-4321",
            "11)98765-4321",
            "(11 98765-4321",
            "(11) 98765-4321",
            "11 98765 4321",
            "11 987654321"
    })
    void deveRegistrarTransacaoComChavePixCelularValida(String chave) {
        ChavePix chavePix = chavePixFactory.criarChavePix(chave, TipoChavePix.CELULAR);
        assertEquals(chave, chavePix.getChave());
        assertEquals(TipoChavePix.CELULAR, chavePix.getTipo());
    }

    @ParameterizedTest
    @CsvSource({
            "1198765432",
            "(1198765%-4321",
            "(11 98765-4321)",
            "11 98765--4321",
            "11 987654321    -",
            "1198765-4321)",
            "1198)765-4321",
            "@@@@@@@@@@@"
    })
    void deveLancarExcecaoParaChavePixCelularInvalida(String chave) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chavePixFactory.criarChavePix(chave, TipoChavePix.CELULAR)
        );
        assertEquals("Chave Pix do tipo Celular inválida.", exception.getMessage());
    }

}