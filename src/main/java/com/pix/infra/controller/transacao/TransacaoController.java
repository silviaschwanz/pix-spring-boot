package com.pix.infra.controller.transacao;

import com.pix.application.usecases.transacao.RealizarTransacao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    @Autowired
    RealizarTransacao realizarTransacao;


    @PostMapping
    @Transactional
    public ResponseEntity<RealizarTransacaoResponse> transferir(
            @RequestBody @Valid RealizarTransacaoRequest request, UriComponentsBuilder uriBuilder
    ) {
        RealizarTransacaoResponse response =   realizarTransacao.executar(request);
        var uri = uriBuilder.path("transacoes/{uuid}").buildAndExpand(response.uuid()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

}
