package com.pix.infra.persistence.chave;

import com.pix.infra.persistence.transacao.TransacaoEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "transacao_chave_pix")
public class TransacaoChavePixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transacao_id", nullable = false)
    private TransacaoEntity transacao;

    @ManyToOne
    @JoinColumn(name = "chave_pix_id", nullable = false)
    private ChavePixEntity chavePix;

    @Column(nullable = false)
    private String tipoLigacao;

    public TransacaoChavePixEntity() {
    }

    public TransacaoChavePixEntity(
            TransacaoEntity transacao,
            ChavePixEntity chavePixEntity,
            String tipoLigacao
    ) {
        this.transacao = transacao;
        this.chavePix = chavePixEntity;
        this.tipoLigacao = tipoLigacao;
    }

    public Long getId() {
        return id;
    }

    public TransacaoEntity getTransacao() {
        return transacao;
    }

    public ChavePixEntity getChavePix() {
        return chavePix;
    }

    public String getTipoLigacao() {
        return tipoLigacao;
    }

}
