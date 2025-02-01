package com.pix.domain.chave;

import com.pix.domain.chave.tipo.*;

import java.util.UUID;

public class ChavePixFactoryImpl implements ChavePixFactory{

    @Override
    public ChavePix criarChavePix(String chave, TipoChavePix tipo) {
        if (chave == null || chave.isBlank()) {
            throw new IllegalArgumentException("Valor da chave PIX não pode ser nulo ou vazio.");
        }
        if (tipo == null || tipo.name().isBlank()) {
            throw new IllegalArgumentException("O tipo da chave PIX não pode ser nulo ou vazio.");
        }
        return switch (tipo) {
            case EMAIL -> EmailPix.criar(chave, tipo);
            case CELULAR -> CelularPix.criar(chave, tipo);
            case CPF -> CpfPix.criar(chave, tipo);
            case CNPJ -> CnpjPix.criar(chave, tipo);
            case ALEATORIA -> ChaveAleatoriaPix.criar(chave, tipo);
        };
    }

    @Override
    public ChavePix restaurarChavePix(UUID uuid, String chave, TipoChavePix tipo) {
        if (chave == null) {
            throw new IllegalArgumentException("Valor da chave PIX não pode ser nulo para restauração.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de chave não pode ser nulo para restauração.");
        }
        return switch (tipo) {
            case EMAIL -> EmailPix.restaurar(uuid ,chave, tipo);
            case CELULAR -> CelularPix.restaurar(uuid, chave, tipo);
            case CPF -> CpfPix.restaurar(uuid, chave, tipo);
            case CNPJ -> CnpjPix.restaurar(uuid ,chave, tipo);
            case ALEATORIA -> ChaveAleatoriaPix.restaurar(uuid, chave, tipo);
        };
    }

}
