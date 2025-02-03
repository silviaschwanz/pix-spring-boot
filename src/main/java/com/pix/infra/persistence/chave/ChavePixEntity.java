package com.pix.infra.persistence.chave;

import com.pix.infra.persistence.TransacaoChavePixEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "chave_pix")
public class ChavePixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String chave;

    @Column(nullable = false)
    private String tipoPix;

    @OneToMany(mappedBy = "chavePix", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TransacaoChavePixEntity> transacoes = new HashSet<>();

    public ChavePixEntity() {
    }

    public ChavePixEntity(UUID uuid, String chave, String tipo) {
        this.uuid = uuid;
        this.chave = chave;
        this.tipoPix = tipo;
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getChave() {
        return chave;
    }

    public String getTipoPix() {
        return tipoPix;
    }

    public Set<TransacaoChavePixEntity> getTransacoes() {
        return transacoes;
    }

}
