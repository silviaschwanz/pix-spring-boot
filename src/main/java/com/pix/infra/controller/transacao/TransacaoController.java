package com.pix.infra.controller.transacao;

import com.pix.application.usecases.transacao.ListarTransacoesEnviadasChaveOrigem;
import com.pix.application.usecases.transacao.RealizarTransacao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    @Autowired
    RealizarTransacao realizarTransacao;

    @Autowired
    ListarTransacoesEnviadasChaveOrigem listarTransacoesChaveOrigem;

    @PostMapping
    public ResponseEntity<RealizarTransacaoResponse> transferir(
            @RequestBody @Valid RealizarTransacaoRequest request, UriComponentsBuilder uriBuilder
    ) {
        RealizarTransacaoResponse response =   realizarTransacao.executar(request);
        var uri = uriBuilder.path("transacoes/{uuid}").buildAndExpand(response.uuid()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{chave}")
    public ResponseEntity<ListarTransacaoResponse> listarTransacoesEnviadasDaChaveOrigem(
            @PathVariable String chave,
            @PageableDefault(size = 10, sort = {"chavePixOrigem"}) Pageable paginacao
    ) {
        ListarTransacaoResponse response = listarTransacoesChaveOrigem.executar(chave, paginacao);
        return ResponseEntity.ok().body(response);
    }

}
