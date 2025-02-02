package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CpfPixTest {

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    @ParameterizedTest
    @CsvSource({
            "12345679, Chave Pix do tipo CPF inválida - formato incorreto.",
            "11.222.333/0001, Chave Pix do tipo CPF inválida - formato incorreto.",
            "1122233300018, Chave Pix do tipo CPF inválida - formato incorreto.",
            "abc.def.ghi/jkl-mn, Chave Pix do tipo CPF inválida - formato incorreto.",
            "00000000000, Chave Pix do tipo CPF inválida - todos os digítos são iguais.",
            "11.222.333/0001-10, Chave Pix do tipo CPF inválida - formato incorreto."
    })
    void deveLancarExcecaoParaChavePixCPFInvalida(String chave, String erroEsperado) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chavePixFactory.criarChavePix(chave, TipoChavePix.CPF)
        );
        assertEquals(erroEsperado, exception.getMessage());
    }

}