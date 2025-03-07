package com.pix.infra.gateways.repository.mapper;

import com.pix.domain.chave.ChavePix;
import com.pix.domain.chave.ChavePixFactory;
import com.pix.domain.chave.ChavePixFactoryImpl;
import com.pix.domain.chave.tipo.TipoChavePix;
import com.pix.infra.persistence.chave.ChavePixEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChavePixMapper {

    private final ChavePixFactory chavePixFactory = new ChavePixFactoryImpl();

    public ChavePix toDomain(ChavePixEntity chavePixEntity) {
        TipoChavePix tipoChavePix = TipoChavePix.valueOf(chavePixEntity.getTipoPix());
        return chavePixFactory.restaurarChavePix(chavePixEntity.getUuid(), chavePixEntity.getChave(), tipoChavePix);
    }

    public ChavePixEntity toEntity(ChavePix chavePix) {
        return new ChavePixEntity(chavePix.getUuid(),chavePix.getChave(), chavePix.getTipo().name());
    }
}
