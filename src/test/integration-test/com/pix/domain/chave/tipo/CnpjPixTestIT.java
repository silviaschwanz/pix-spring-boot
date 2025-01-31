package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
class CnpjPixTestIT {

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    @Autowired
    private CnpjTestProperties cnpjTestProperties;

    private Stream<String> fornecerCnpjsValidos() {
        return Stream.of(
                cnpjTestProperties.getValid1(),
                cnpjTestProperties.getValid2(),
                cnpjTestProperties.getValid3(),
                cnpjTestProperties.getValid4(),
                cnpjTestProperties.getValid5(),
                cnpjTestProperties.getValid6()
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