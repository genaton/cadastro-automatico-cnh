CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(255),
    data_nascimento DATE,
    local_nascimento VARCHAR(255),
    uf_nascimento CHAR(2),
    rg_numero VARCHAR(20),
    rg_orgao_emissor VARCHAR(20),
    rg_uf CHAR(2),
    cpf CHAR(11),
    numero_registro VARCHAR(30),
    data_emissao DATE,
    nacionalidade VARCHAR(50),
    filiacao VARCHAR(255),
    contrato_pdf LONGBLOB
);

CREATE TABLE agencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_agencia VARCHAR(10),
    numero_conta VARCHAR(15),
    cliente_id BIGINT,
    CONSTRAINT fk_agencia_cliente FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
);