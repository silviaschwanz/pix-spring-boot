package com.pix.domain.chave.tipo;

import com.pix.domain.chave.ChavePix;

import java.util.regex.Pattern;

public class EmailPix extends ChavePix {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private EmailPix(String chave, TipoChavePix tipo) {
        super(chave, tipo);
    }

    public static ChavePix criar(String chave, TipoChavePix tipo) {
        validarFormato(chave);
        return new EmailPix(chave, tipo);
    }

    public static ChavePix restaurar(String chave, TipoChavePix tipo) {
        return new EmailPix(chave, tipo);
    }

    private static void validarFormato(String chave) {
        if (!EMAIL_PATTERN.matcher(chave).matches()) {
            throw new IllegalArgumentException("Chave Pix do tipo Email inválida.");
        }
    }

}
