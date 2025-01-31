package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.regex.Pattern;

public record CelularPix(String chave, TipoChavePix tipo) implements ChavePix {

    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\(?(\\d{2})\\)? ?9\\d{4}[- ]?\\d{4}$");

    public CelularPix {
        if (!CELULAR_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException("Chave Pix do tipo Celular inválida.");
        }
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
