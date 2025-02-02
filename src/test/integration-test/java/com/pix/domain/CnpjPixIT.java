package com.pix.domain;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
class CnpjPixIT {

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    @Autowired
    Environment environment;

    private Stream<String> fornecerCnpjsValidos() {
        return Stream.of(
                environment.getProperty("cnpj.test.valid1"),
                environment.getProperty("cnpj.test.valid2"),
                environment.getProperty("cnpj.test.valid3"),
                environment.getProperty("cnpj.test.valid4"),
                environment.getProperty("cnpj.test.valid5"),
                environment.getProperty("cnpj.test.valid6")
        );
    }

    @ParameterizedTest
    @MethodSource("fornecerCnpjsValidos")
    void deveRegistrarTransacaoComChavePixCNPJValida(String chave) {
        ChavePix chavePix = chavePixFactory.criarChavePix(chave, TipoChavePix.CNPJ);
        String chaveEsperada = chave.replaceAll("\\D", "");
        assertEquals(chaveEsperada, chavePix.getChave());
        assertEquals(TipoChavePix.CNPJ, chavePix.getTipo());
    }

}