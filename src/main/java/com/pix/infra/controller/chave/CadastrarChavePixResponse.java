package com.pix.infra.controller.chave;

import java.util.UUID;

public record CadastrarChavePixResponse(
        UUID uuid,
        String chave,
        String tipo
) {
}
