CREATE TABLE IF NOT EXISTS horario (
  codigo            BIGINT PRIMARY KEY        NOT NULL,
  data_exame        DATE                      NOT NULL,
  hora_exame        VARCHAR(10)               NOT NULL,
  disponivel        BOOLEAN NOT NULL DEFAULT  FALSE
  );

CREATE SEQUENCE IF NOT EXISTS horarioseq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO horario(codigo, data_exame, hora_exame, disponivel)
VALUES (1, '2018-11-30', '14:00', true),
(2, '2018-11-30', '15:00', false),
(3, '2018-12-01', '10:30', true),
(4, '2018-12-01', '11:30', true),
(5, '2018-12-01', '13:30', true),
(6, '2018-12-01', '14:30', true),
(7, '2018-12-02', '11:30', true),
(8, '2018-12-02', '16:00', true);

CREATE TABLE IF NOT EXISTS agendamento (
  codigo              BIGINT PRIMARY KEY  NOT NULL,
  observacao          VARCHAR(100),
  tipo                VARCHAR(50)         NOT NULL,
  codigo_horario      BIGINT              NOT NULL,
  codigo_funcionario  BIGINT              NOT NULL,
  FOREIGN KEY (codigo_horario)            REFERENCES horario(codigo),
  FOREIGN KEY (codigo_funcionario)        REFERENCES funcionario(codigo)

  );

CREATE SEQUENCE IF NOT EXISTS agendamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO agendamento(codigo, observacao, tipo, codigo_horario, codigo_funcionario)
VALUES (1, 'Pagar guia na hora', 'RENOVACAO_PORTE', 4, 1), (2, '', 'ADMISSAO', 1, 3),
(3, '', 'RENOVACAO_PORTE', 5, 2);


