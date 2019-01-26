CREATE TABLE IF NOT EXISTS usuario (

	codigo BIGINT   PRIMARY KEY   NOT NULL,
	nome            VARCHAR(50)   NOT NULL,
	email           VARCHAR(50)   UNIQUE NOT NULL,
	senha           VARCHAR(150)  NOT NULL,
	anexo           VARCHAR(255)

);

CREATE SEQUENCE IF NOT EXISTS usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE IF NOT EXISTS permissao (
	codigo BIGINT PRIMARY KEY NOT NULL,
	descricao VARCHAR(50) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS permissao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE IF NOT EXISTS  usuario_permissao (
	codigo_usuario BIGINT NOT NULL,
	codigo_permissao BIGINT NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);


INSERT INTO usuario (codigo, nome, email, senha) values (nextval('usuario_seq'), 'Administrador',
'admin@lufamador.com.br', '$2a$10$0SRMSVY60PiIfnDfnN6uGOFSbxPirSmkAwhKVlMEzXf4qCppA/cwi');

INSERT INTO permissao (codigo, descricao) values (1, 'ROLE_ADMIN');
INSERT INTO permissao (codigo, descricao) values (2, 'ROLE_SECRETARIA');
INSERT INTO permissao (codigo, descricao) values (3, 'ROLE_FINANCEIRO');
INSERT INTO permissao (codigo, descricao) values (4, 'ROLE_ADM_JOGOS');
INSERT INTO permissao (codigo, descricao) values (5, 'ROLE_DEFAULT');

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
