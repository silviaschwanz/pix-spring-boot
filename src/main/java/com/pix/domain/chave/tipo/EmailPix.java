package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.regex.Pattern;

public record EmailPix(String chave, TipoChavePix tipo) implements ChavePix {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public EmailPix(String chave, TipoChavePix tipo) {
        if (!EMAIL_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException("Chave Pix do tipo Email inválida.");
        }
        this.chave = chave;
        this.tipo = tipo;
    }

    @Override
    public String getChave() {
        return chave;
    }

    @Override
    public TipoChavePix getTipo() {
        return TipoChavePix.EMAIL;
    }

}
