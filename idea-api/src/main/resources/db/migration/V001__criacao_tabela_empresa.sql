CREATE TABLE IF NOT EXISTS empresa (
  codigo        BIGINT PRIMARY KEY  NOT NULL,
  nome          VARCHAR(100)        NOT NULL,
  ativa         BOOLEAN             NOT NULL DEFAULT FALSE,
  cnpj          VARCHAR(20)         NOT NULL,
  telefone      VARCHAR(20)         NOT NULL,
  email         VARCHAR(50),
  logradouro    VARCHAR(100),
  numero        VARCHAR(10),
  complemento   VARCHAR(100),
  bairro        VARCHAR(50),
  cep           VARCHAR(20),
  cidade        VARCHAR(50),
  estado        VARCHAR(20)
  );

CREATE SEQUENCE IF NOT EXISTS empresa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO empresa(codigo, nome, ativa, cnpj, telefone, email, logradouro, numero, complemento, bairro, cep, cidade,
 estado)
 VALUES
 (1, 'RODOBAN', true, '23961535000124', '3433334444', 'email@rodoban.com.br', 'Av. Floriano ' ||
  'Peixoto', '3550', '', 'Brasil', '38408177', 'Uberlândia', 'MG'),
  (2, 'PROSSEGUR', true, '12345678912536', '3477778855', 'email@prossegur.com.br', 'Av. ' ||
   'Monsenhor Eduardo', '10', '', 'Brasilia', '38408199', 'Uberlândia', 'MG');


