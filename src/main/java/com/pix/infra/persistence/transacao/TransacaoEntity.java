package com.pix.infra.persistence.transacao;

import com.pix.infra.persistence.chave.ChavePixEntity;
import com.pix.infra.persistence.chave.TransacaoChavePixEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "transacao")
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TransacaoChavePixEntity> transacaoChaves = new HashSet<>();

    public TransacaoEntity() {
    }

    public TransacaoEntity(
            UUID uuid,
            BigDecimal valor,
            LocalDateTime dataHora
    ) {
        this.uuid = uuid;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public void adicionarChave(ChavePixEntity chavePix, String tipoLigacao) {
        TransacaoChavePixEntity transacaoChavePix = new TransacaoChavePixEntity(
                this, chavePix, tipoLigacao
        );
        transacaoChaves.add(transacaoChavePix);
        chavePix.getTransacoes().add(transacaoChavePix);
    }

    public void removerChave(ChavePixEntity chavePix) {
        transacaoChaves.removeIf(tcp -> tcp.getChavePix().equals(chavePix));
        chavePix.getTransacoes().removeIf(tcp -> tcp.getTransacao().equals(this));
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Set<TransacaoChavePixEntity> getTransacaoChaves() {
        return transacaoChaves;
    }
}
