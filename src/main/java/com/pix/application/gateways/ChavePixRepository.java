package com.pix.application.gateways;

import com.pix.domain.chave.ChavePix;

public interface ChavePixRepository {

    ChavePix salvar(ChavePix chavePix);
    ChavePix buscarPorChave(String chave);

}
