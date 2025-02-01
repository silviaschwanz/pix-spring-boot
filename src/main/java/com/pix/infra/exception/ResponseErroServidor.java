package com.pix.infra.exception;

public record ResponseErroServidor(String mensagem, String classeExcecao) {

    public ResponseErroServidor(Exception exception) {
        this(exception.getLocalizedMessage(), exception.getClass().toString());
    }
}
