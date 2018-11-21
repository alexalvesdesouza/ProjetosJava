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


