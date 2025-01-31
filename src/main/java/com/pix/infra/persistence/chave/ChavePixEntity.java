package com.pix.infra.persistence.chave;

import jakarta.persistence.*;

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
    private String tipo;

    public ChavePixEntity(UUID uuid, String chave, String tipo) {
        this.uuid = uuid;
        this.chave = chave;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

}
