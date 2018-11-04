CREATE TABLE IF NOT EXISTS pessoa (
  codigo        BIGINT PRIMARY KEY  NOT NULL,
  nome          VARCHAR(50)         NOT NULL,
  ativa         BOOLEAN             NOT NULL DEFAULT FALSE,
  logradouro    VARCHAR(100),
  numero        VARCHAR(10),
  complemento   VARCHAR(100),
  bairro        VARCHAR(50),
  cep           VARCHAR(20),
  cidade        VARCHAR(50),
  estado        VARCHAR(20)
  );

CREATE SEQUENCE IF NOT EXISTS pessoa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
