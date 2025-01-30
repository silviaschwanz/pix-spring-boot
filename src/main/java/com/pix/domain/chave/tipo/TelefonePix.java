package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

public record TelefonePix(String valorChave) implements ChavePix {

    @Override
    public TipoChavePix getTipo() {
        return TipoChavePix.TELEFONE;
    }
}
