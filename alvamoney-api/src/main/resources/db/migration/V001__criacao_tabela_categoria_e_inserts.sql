CREATE TABLE IF NOT EXISTS categoria (
  codigo        BIGINT PRIMARY KEY          NOT NULL,
  nome          VARCHAR(50)                 NOT NULL
  );

CREATE SEQUENCE IF NOT EXISTS categoria_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

INSERT INTO categoria(codigo, nome)
VALUES
(nextval('categoria_seq'), 'Lazer'),
(nextval('categoria_seq'), 'Alimentação'),
(nextval('categoria_seq'), 'Supermercado'),
(nextval('categoria_seq'), 'Farmácia'),
(nextval('categoria_seq'), 'Outros');