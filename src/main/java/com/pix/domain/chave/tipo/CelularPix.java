package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.UUID;
import java.util.regex.Pattern;

public class CelularPix extends ChavePix {

    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\(?(\\d{2})\\)? ?9\\d{4}[- ]?\\d{4}$");

    private CelularPix(String chave, TipoChavePix tipo) {
        super(chave, tipo);
        validarFormato();
    }

    private CelularPix(UUID uuid, String chave, TipoChavePix tipo) {
        super(uuid, chave, tipo);
    }

    public static ChavePix criar(String chave, TipoChavePix tipo) {
        return new CelularPix(chave, tipo);
    }

    public static ChavePix restaurar(UUID uuid, String chave, TipoChavePix tipo) {
        return new CelularPix(uuid, chave, tipo);
    }

    private void validarFormato() {
        if (!CELULAR_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException("Chave Pix do tipo Celular inválida.");
        }
    }

}
