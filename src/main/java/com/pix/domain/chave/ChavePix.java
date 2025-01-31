package com.pix.domain.chave;

import com.pix.domain.chave.tipo.TipoChavePix;

import java.util.UUID;

public abstract class ChavePix {

    private final String chave;
    private final TipoChavePix tipo;
    private final UUID uuid;

    protected ChavePix(String chave, TipoChavePix tipo) {
        this.chave = chave;
        this.tipo = tipo;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getChave() {
        return chave;
    }

    public TipoChavePix getTipo() {
        return tipo;
    }
}
