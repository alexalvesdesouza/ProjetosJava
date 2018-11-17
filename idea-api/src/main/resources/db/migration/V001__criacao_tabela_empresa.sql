CREATE TABLE IF NOT EXISTS empresa (

  codigo        BIGINT PRIMARY KEY  NOT NULL,
  razao_social  VARCHAR(100)        NOT NULL,
  nome_fantasia VARCHAR(100),
  cnpj          VARCHAR(20)         NOT NULL,
  contato       VARCHAR(50)         NOT NULL,
  telefone      VARCHAR(20)         NOT NULL,
  email         VARCHAR(50),
  logradouro    VARCHAR(100),
  numero        VARCHAR(10),
  complemento   VARCHAR(100),
  bairro        VARCHAR(50),
  cep           VARCHAR(20),
  cidade        VARCHAR(50),
  estado        VARCHAR(20),
  ativa         BOOLEAN             NOT NULL DEFAULT FALSE
  );

CREATE SEQUENCE IF NOT EXISTS empresa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO empresa(codigo, razao_social, nome_fantasia, cnpj, contato, telefone, email, logradouro, numero,
complemento, bairro, cep, cidade,  estado)
 VALUES
  (1, 'RODOBAN', 'RODOBAN TRANSPORTES', '23961535000124', 'Atendente 01', '3433334444', 'email@rodoban.com.br'
  , 'Av. Floriano Peixoto', '3550', '', 'Brasil', '38408177', 'Uberlândia', 'MG'),

  (2, 'PROSSEGUR', 'Prossegur valores', '12345678912536', 'Telefonista', '3477778855', 'email@prossegur.com.br'
  ,'Av. Monsenhor Eduardo', '10', '', 'Brasilia', '38408199', 'Uberlândia', 'MG');


