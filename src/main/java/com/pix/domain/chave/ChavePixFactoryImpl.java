package com.pix.domain.chave;

import com.pix.domain.chave.tipo.*;

public class ChavePixFactoryImpl implements ChavePixFactory{

    @Override
    public ChavePix criarChavePix(String valorChave) {
        if (valorChave == null || valorChave.isBlank()) {
            throw new IllegalArgumentException("Valor da chave PIX não pode ser nulo ou vazio.");
        }
        if (padraoEmail(valorChave)) {
            return new EmailPix(valorChave);
        }
        if (padraoCPF(valorChave)) {
            return new CpfPix(valorChave);
        }
        if (padraoCNPJ(valorChave)) {
            return new CnpjPix(valorChave);
        }
        if (padraoTelefone(valorChave)) {
            return new TelefonePix(valorChave);
        }
        if (padraoUuidChaveAleatoria(valorChave)) {
            return new ChaveAleatoriaPix(valorChave);
        }
        throw new IllegalArgumentException("Chave PIX inválida.");
    }

    private boolean padraoEmail(String valorChave) {
        return valorChave.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean padraoCPF(String valorChave) {
        return  valorChave.matches("\\d{11}");
    }

    private boolean padraoCNPJ(String valorChave) {
        return  valorChave.matches("\\d{14}");
    }

    private boolean padraoTelefone(String valorChave) {
        return  valorChave.matches("^\\+\\d{1,15}$");
    }

    private boolean padraoUuidChaveAleatoria(String valorChave) {
        return  valorChave.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89aAbB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$");
    }

}
