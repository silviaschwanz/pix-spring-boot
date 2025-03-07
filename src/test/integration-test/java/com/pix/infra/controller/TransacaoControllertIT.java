package com.pix.infra.controller;

import com.pix.ApiConstantes;
import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import com.pix.infra.controller.chave.ChavePixRequest;
import com.pix.infra.controller.transacao.TransacaoRequest;
import com.pix.infra.gateways.repository.ChavePixServiceRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TransacaoControllertIT {

    @LocalServerPort
    int port;

    @Autowired
    Flyway flyway;

    @Autowired
    ChavePixServiceRepository chavePixServiceRepository;

    String pathResource = ApiConstantes.BASE_PATH + "/transacoes";


    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Deve realizar uma transferência entre chaves pix ao receber uma request válida")
    void transferir() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePixOrigem = chavePixFactory.criarChavePix("frt@gmail.com", TipoChavePix.EMAIL);
        ChavePix chavePixDestino = chavePixFactory.criarChavePix("gil@gmail.com", TipoChavePix.EMAIL);
        chavePixServiceRepository.salvar(chavePixOrigem);
        chavePixServiceRepository.salvar(chavePixDestino);

        TransacaoRequest request = new TransacaoRequest(
                new ChavePixRequest("frt@gmail.com", TipoChavePix.EMAIL),
                new ChavePixRequest("gil@gmail.com", TipoChavePix.EMAIL),
                new BigDecimal("4000.00")
        );
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(pathResource)
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("uuid", notNullValue())
                .body("valor", is("4000.00"))
                .body("chavePixOrigem", is("frt@gmail.com"))
                .body("chavePixDestino", is("gil@gmail.com"))
                .extract().response();
        response.prettyPrint();
    }

    @Test
    @DisplayName("Deve lançar exceção EntityNotFoundException ao tentar realizar transferência com chave pix origem " +
            "inexistente no database")
    void naoDeveTransferirSemChavePixOrigem() {
        ChavePixFactoryImpl chavePixFactory = new ChavePixFactoryImpl();
        ChavePix chavePixDestino = chavePixFactory.criarChavePix("gil@gmail.com", TipoChavePix.EMAIL);
        chavePixServiceRepository.salvar(chavePixDestino);

        TransacaoRequest request = new TransacaoRequest(
                new ChavePixRequest("frt@gmail.com", TipoChavePix.EMAIL),
                new ChavePixRequest("gil@gmail.com", TipoChavePix.EMAIL),
                new BigDecimal("4000.00")
        );
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(pathResource)
                .then()
                .statusCode(404)
                .body("mensagemErro",is("Chave Pix não encontrada: frt@gmail.com"))
                .extract().response();
        response.prettyPrint();
    }

}