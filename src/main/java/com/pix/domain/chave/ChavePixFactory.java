package com.pix.domain.chave;

import com.pix.domain.chave.tipo.TipoChavePix;

public interface ChavePixFactory {

    ChavePix criarChavePix(String chave, TipoChavePix tipo);
    ChavePix restaurarChavePix(String chave, TipoChavePix tipo);

}
