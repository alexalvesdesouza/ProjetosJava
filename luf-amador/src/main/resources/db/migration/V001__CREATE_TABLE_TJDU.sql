CREATE TABLE IF NOT EXISTS tjdu (

  codigo        BIGINT PRIMARY KEY  NOT NULL,
  numero         VARCHAR(50)         NOT NULL,
  categoria      VARCHAR(20)         NOT NULL,
  anexo         VARCHAR(25)

);

CREATE SEQUENCE IF NOT EXISTS tjdu_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;