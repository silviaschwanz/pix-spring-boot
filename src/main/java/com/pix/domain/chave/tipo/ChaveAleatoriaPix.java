package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.regex.Pattern;

public record ChaveAleatoriaPix(String chave, TipoChavePix tipo) implements ChavePix {

    private static final Pattern CHAVE_ALEATORIA_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89aAbB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );

    public ChaveAleatoriaPix(String chave, TipoChavePix tipo) {
        if (!CHAVE_ALEATORIA_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException("Chave Pix do tipo Aleatória inválida.");
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
        return tipo;
    }

}
