package com.pix.domain.chave;

import com.pix.domain.chave.tipo.TipoChavePix;

import java.util.UUID;

public interface ChavePixFactory {

    ChavePix criarChavePix(String chave, TipoChavePix tipo);
    ChavePix restaurarChavePix(UUID uuid, String chave, TipoChavePix tipo);

}
