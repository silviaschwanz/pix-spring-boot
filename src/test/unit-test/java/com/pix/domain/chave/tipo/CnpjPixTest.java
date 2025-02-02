package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CnpjPixTest {

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    @ParameterizedTest
    @CsvSource({
            "123456780001959, Chave Pix do tipo CNPJ inválida - formato incorreto.",
            "11.222.333/0001, Chave Pix do tipo CNPJ inválida - formato incorreto.",
            "1122233300018, Chave Pix do tipo CNPJ inválida - formato incorreto.",
            "abc.def.ghi/jkl-mn, Chave Pix do tipo CNPJ inválida - formato incorreto.",
            "00000000000000, Chave Pix do tipo CNPJ inválida - todos os digítos são iguais.",
            "11.222.333/0001-10, Chave Pix do tipo CNPJ inválida - dígitos verificadores inválidos."
    })
    void deveLancarExcecaoParaChavePixCNPJInvalida(String chave, String erroEsperado) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> chavePixFactory.criarChavePix(chave, TipoChavePix.CNPJ)
        );
        assertEquals(erroEsperado, exception.getMessage());
    }

}