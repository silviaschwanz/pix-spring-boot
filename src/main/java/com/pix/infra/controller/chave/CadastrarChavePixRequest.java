package com.pix.infra.controller.chave;

import com.pix.domain.chave.tipo.TipoChavePix;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarChavePixRequest(
        @NotBlank(message = "A chave pix não foi informada.")
        String chave,

        @NotNull(message = "O tipo de chave pix não foi informada.")
        TipoChavePix tipoChave
) {
}
