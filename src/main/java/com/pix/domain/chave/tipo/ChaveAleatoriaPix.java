package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.UUID;
import java.util.regex.Pattern;

public class ChaveAleatoriaPix extends ChavePix {

    private static final Pattern CHAVE_ALEATORIA_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89aAbB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );

    private ChaveAleatoriaPix(String chave, TipoChavePix tipo) {
        super(chave, tipo);
        validarFormato();
    }

    private ChaveAleatoriaPix(UUID uuid, String chave, TipoChavePix tipo) {
        super(uuid, chave, tipo);
    }

    public static ChavePix criar(String chave, TipoChavePix tipo) {
        return new ChaveAleatoriaPix(chave, tipo);
    }

    public static ChavePix restaurar(UUID uuid, String chave, TipoChavePix tipo) {
        return new ChaveAleatoriaPix(uuid, chave, tipo);
    }

    private void validarFormato() {
        if (!CHAVE_ALEATORIA_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException("Chave Pix do tipo Aleatória inválida.");
        }
    }
}
