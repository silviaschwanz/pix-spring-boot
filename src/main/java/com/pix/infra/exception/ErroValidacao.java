package com.pix.infra.exception;

import org.springframework.validation.FieldError;

public record ErroValidacao(
        String campo,
        String mensagem
) {
    public ErroValidacao(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
