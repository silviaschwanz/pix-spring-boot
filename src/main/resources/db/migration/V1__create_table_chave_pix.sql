CREATE TABLE chave_pix (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    chave VARCHAR(254) NOT NULL UNIQUE,
    tipo_pix VARCHAR(9) NOT NULL,
    CONSTRAINT chk_tipo_pix CHECK (tipo_pix IN ('CPF', 'CNPJ', 'EMAIL', 'CELULAR', 'ALEATORIA'))
);

CREATE TABLE transacao (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    valor NUMERIC(18, 2) NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE transacao_chave_pix (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    transacao_id BIGINT NOT NULL,
    chave_pix_id BIGINT NOT NULL,
    tipo_ligacao VARCHAR(7) NOT NULL,
    CONSTRAINT fk_transacao_id FOREIGN KEY (transacao_id) REFERENCES transacao(id),
    CONSTRAINT fk_chave_pix_id FOREIGN KEY (chave_pix_id) REFERENCES chave_pix(id)
);

CREATE INDEX idx_tipo_ligacao_chave_pix_include_transacao ON transacao_chave_pix(tipo_ligacao, chave_pix_id) INCLUDE (transacao_id);
CREATE INDEX idx_uuid_transacao ON transacao(uuid);
CREATE INDEX idx_uuid_chave_pix ON chave_pix(uuid);

