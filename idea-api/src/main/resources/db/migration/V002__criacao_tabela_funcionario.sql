CREATE TABLE IF NOT EXISTS funcionario (
  codigo            BIGINT PRIMARY KEY  NOT NULL,
  nome              VARCHAR(50)         NOT NULL,
  rg                VARCHAR(50),
  cpf               VARCHAR(20)         UNIQUE,
  email             VARCHAR(50),
  matricula         VARCHAR(20),
  cargo             VARCHAR(50),
  data_nascimento   DATE                NOT NULL,
  telefone          VARCHAR(20),
  logradouro        VARCHAR(100),
  numero            VARCHAR(10),
  complemento       VARCHAR(100),
  bairro            VARCHAR(50),
  cep               VARCHAR(20),
  cidade            VARCHAR(50),
  estado            VARCHAR(20)
  );

CREATE SEQUENCE IF NOT EXISTS funcionario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

