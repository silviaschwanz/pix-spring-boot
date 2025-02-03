package com.pix.infra.persistence.transacao;

import com.pix.infra.persistence.chave.ChavePixEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacao")
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chave_pix_origem_id", nullable = false)
    private ChavePixEntity chavePixOrigem;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chave_pix_destino_id", nullable = false)
    private ChavePixEntity chavePixDestino;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    public TransacaoEntity() {
    }

    public TransacaoEntity(UUID uuid, ChavePixEntity chavePixOrigem, BigDecimal valor, ChavePixEntity chavePixDestino) {
        this.chavePixOrigem = chavePixOrigem;
        this.valor = valor;
        this.chavePixDestino = chavePixDestino;
        this.uuid = uuid;
        this.dataHora = LocalDateTime.now();
    }

    @PrePersist
    private void validarChaves() {
        if (chavesSaoIguais()) {
            throw new IllegalArgumentException("A chave Pix de origem não pode ser igual à chave Pix de destino.");
        }
    }

    public boolean chavesSaoIguais() {
        if (chavePixOrigem == null || chavePixDestino == null) {
            return false;
        }
        return chavePixOrigem.getChave().equals(chavePixDestino.getChave());
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ChavePixEntity getChavePixOrigem() {
        return chavePixOrigem;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ChavePixEntity getChavePixDestino() {
        return chavePixDestino;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

}
