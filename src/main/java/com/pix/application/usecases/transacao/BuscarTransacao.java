package com.pix.application.usecases.transacao;

import com.pix.application.gateways.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarTransacao {

    @Autowired
    TransacaoRepository transacaoRepository;

}
