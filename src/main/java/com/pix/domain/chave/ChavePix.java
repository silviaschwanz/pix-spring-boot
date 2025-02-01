package com.pix.domain.chave;

import com.pix.domain.chave.tipo.TipoChavePix;

import java.util.UUID;

public abstract class ChavePix {

    protected final String chave;
    protected final TipoChavePix tipo;
    protected final UUID uuid;

    protected ChavePix(String chave, TipoChavePix tipo) {
        this.chave = chave;
        this.tipo = tipo;
        this.uuid = UUID.randomUUID();
    }

    protected ChavePix(UUID uuid, String chave, TipoChavePix tipo) {
        this.chave = chave;
        this.tipo = tipo;
        this.uuid = uuid;
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
