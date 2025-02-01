package com.pix.infra.controller.chave;

import com.pix.domain.chave.tipo.TipoChavePix;
import jakarta.validation.constraints.NotBlank;

public record CadastrarChavePixRequest(
        @NotBlank(message = "A chave pix não foi informada.")
        String chave,

        @NotBlank(message = "O tipo de chave pix não foi informada.")
        TipoChavePix tipoChave
) {
}
