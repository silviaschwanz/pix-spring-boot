package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailPixTest {

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    @ParameterizedTest
    @CsvSource({
            "123@gmail.com",
            "soul123@gmail.com",
            "123@gmail.com.br",
            "123@empresa.com",
            "123@empresa.br",
    })
    void deveRegistrarTransacaoComChavePixEmailValida(String chave) {
        ChavePix chavePix = chavePixFactory.criarChavePix(chave, TipoChavePix.EMAIL);
        assertEquals(chave, chavePix.getChave());
        assertEquals(TipoChavePix.EMAIL, chavePix.getTipo());
    }

    @ParameterizedTest
    @CsvSource({
            "123@gmail.com)",
            "soul123@gmail.com?",
            "123@gmail.com.br*",
            "123.gmail.com",
            "123@10",
            "123@empresa",
    })
    void deveLancarExcecaoParaChavePixEmailInvalida(String chave) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chavePixFactory.criarChavePix(chave, TipoChavePix.EMAIL)
        );
        assertEquals("Chave Pix do tipo Email inválida.", exception.getMessage());
    }

}