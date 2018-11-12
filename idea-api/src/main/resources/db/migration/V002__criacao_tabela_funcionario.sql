CREATE TABLE IF NOT EXISTS funcionario (
  codigo            BIGINT PRIMARY KEY  NOT NULL,
  nome              VARCHAR(50)         NOT NULL,
  matricula         VARCHAR(20),
  rg                VARCHAR(50),
  cpf               VARCHAR(20),
  telefone          VARCHAR(20),
  logradouro        VARCHAR(100),
  numero            VARCHAR(10),
  complemento       VARCHAR(100),
  bairro            VARCHAR(50),
  cep               VARCHAR(20),
  cidade            VARCHAR(50),
  estado            VARCHAR(20),
  codigo_empresa    BIGINT              NOT NULL,
  FOREIGN KEY (codigo_empresa)        REFERENCES empresa(codigo)
  );

CREATE SEQUENCE IF NOT EXISTS funcionario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO funcionario(codigo, nome, matricula, rg, cpf, telefone, logradouro, numero, complemento, bairro,
cep, cidade, estado, codigo_empresa)
 VALUES
 (1, 'Adriano Lucio', '123456', 'MG12345678', '00011122233', '3477777777', 'Av. João naves de ávila', '2815', '',
 'Santa monica', '38408144', 'Uberlândia', 'MG', 1),
 (2, 'Funcionario 02', '12255', 'MG44887755', '77755565212', '3478888888', 'Av. Cesario Alvin', '200', 'teste',
 'Aparecida', '38408122', 'Uberlândia', 'MG', 2),
 (3, 'Funcionario 03', '87515', 'MG998844', '55665514565', '3499999999', 'Av. Mato Grosso', '700', '',
 'Centro', '39404144', 'Araguari', 'MG', 1);
