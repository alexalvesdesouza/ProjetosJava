CREATE TABLE IF NOT EXISTS lancamento (
  codigo            BIGINT PRIMARY KEY  NOT NULL,
  descricao         VARCHAR(50)         NOT NULL,
  data_vencimento   DATE                NOT NULL,
  data_pagamento    DATE,
  valor             DECIMAL(10,2)       NOT NULL,
  observacao        VARCHAR(100),
  tipo              VARCHAR(20)         NOT NULL,
  codigo_categoria  BIGINT              NOT NULL,
  codigo_pessoa     BIGINT              NOT NULL,
  FOREIGN KEY (codigo_categoria)        REFERENCES categoria(codigo),
  FOREIGN KEY (codigo_pessoa)           REFERENCES pessoa(codigo)

  );

CREATE SEQUENCE IF NOT EXISTS lancamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

