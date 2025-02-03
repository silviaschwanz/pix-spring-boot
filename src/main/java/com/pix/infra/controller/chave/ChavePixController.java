package com.pix.infra.controller.chave;

import com.pix.application.usecases.chave.CadastrarChavePix;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("chaves")
public class ChavePixController {

    @Autowired
    CadastrarChavePix cadastrarChavePix;

    @PostMapping
    public ResponseEntity<ChavePixResponse> cadastrar(
            @RequestBody @Valid ChavePixRequest cadastrarChavePixRequest, UriComponentsBuilder uriBuilder
    ) {
        ChavePixResponse response = cadastrarChavePix.executar(cadastrarChavePixRequest);
        var uri = uriBuilder.path("chaves/{uuid}").buildAndExpand(response.uuid()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

}
