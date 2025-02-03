package com.pix.infra.controller.chave;

import java.util.UUID;

public record ChavePixResponse(
        UUID uuid,
        String chave,
        String tipo
) {
}
