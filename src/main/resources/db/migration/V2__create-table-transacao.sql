CREATE TABLE transacao (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    valor NUMERIC(18, 2) NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT NOW(),
    chave_pix_origem_id BIGINT NOT NULL,
    chave_pix_destino_id BIGINT NOT NULL,
    CONSTRAINT fk_chave_pix_origem FOREIGN KEY (chave_pix_origem_id) REFERENCES chave_pix(id),
    CONSTRAINT fk_chave_pix_destino FOREIGN KEY (chave_pix_destino_id) REFERENCES chave_pix(id)
);

CREATE INDEX idx_chave_pix_origem ON transacao(chave_pix_origem_id);
CREATE INDEX idx_chave_pix_destino ON transacao(chave_pix_destino_id);