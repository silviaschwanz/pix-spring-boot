package com.pix.domain.chave;

import com.pix.domain.chave.tipo.*;

public class ChavePixFactoryImpl implements ChavePixFactory{

    @Override
    public ChavePix criarChavePix(String chave, TipoChavePix tipo) {
        if (chave == null || chave.isBlank()) {
            throw new IllegalArgumentException("Valor da chave PIX não pode ser nulo ou vazio.");
        }
        if (tipo == null || tipo.name().isBlank()) {
            throw new IllegalArgumentException("O tipo da chave PIX não pode ser nulo ou vazio.");
        }
        if(tipo.equals(TipoChavePix.EMAIL)){
            return new EmailPix(chave, tipo);
        }
        if(tipo.equals(TipoChavePix.CELULAR)){
            return new CelularPix(chave, tipo);
        }
        if(tipo.equals(TipoChavePix.CPF)){
            return new CpfPix(chave, tipo);
        }
        if(tipo.equals(TipoChavePix.CNPJ)) {
            return new CnpjPix(chave, tipo);
        }
        if(tipo.equals(TipoChavePix.ALEATORIA)) {
            return new ChaveAleatoriaPix(chave, tipo);
        }
        throw new IllegalArgumentException("Chave PIX inválida.");
    }

}
