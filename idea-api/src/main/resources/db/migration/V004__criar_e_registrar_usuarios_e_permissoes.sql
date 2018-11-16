CREATE TABLE IF NOT EXISTS usuario (

	codigo BIGINT PRIMARY KEY NOT NULL,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL

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

-- Senhas: admin, e 123456

INSERT INTO usuario (codigo, nome, email, senha) values (1, 'Alex Alves', 'admin@ideia.com',
'$2a$10$0SRMSVY60PiIfnDfnN6uGOFSbxPirSmkAwhKVlMEzXf4qCppA/cwi');

INSERT INTO usuario (codigo, nome, email, senha) values (2, 'Amanda Pereira', 'amanda.pereira@ideia.com',
'$2a$10$h/GnlG34swLINiSs41IDbO1EzzgdM5X1bYHT6StSHKTzKzkS83wBm');

INSERT INTO usuario (codigo, nome, email, senha) values (3, 'Carlos Santana', 'carlos.santana@ideia.com',
'$2a$10$h/GnlG34swLINiSs41IDbO1EzzgdM5X1bYHT6StSHKTzKzkS83wBm');

INSERT INTO usuario (codigo, nome, email, senha) values (4, 'Nilza', 'nilza@ideia.com',
'$2a$10$0SRMSVY60PiIfnDfnN6uGOFSbxPirSmkAwhKVlMEzXf4qCppA/cwi');

INSERT INTO usuario (codigo, nome, email, senha) values (5, 'Reinaldo', 'reinaldo@ideia.com',
'$2a$10$0SRMSVY60PiIfnDfnN6uGOFSbxPirSmkAwhKVlMEzXf4qCppA/cwi');

INSERT INTO usuario (codigo, nome, email, senha) values (6, 'Raquel T. Santos', 'reinaldo@ideia.com',
'$2a$10$h/GnlG34swLINiSs41IDbO1EzzgdM5X1bYHT6StSHKTzKzkS83wBm');


INSERT INTO permissao (codigo, descricao) values (1, 'ROLE_PESQUISAR_AGENDAMENTO');
INSERT INTO permissao (codigo, descricao) values (2, 'ROLE_CADASTRAR_AGENDAMENTO');
INSERT INTO permissao (codigo, descricao) values (3, 'ROLE_REMOVER_AGENDAMENTO');

INSERT INTO permissao (codigo, descricao) values (4, 'ROLE_PESQUISAR_EMPRESA');
INSERT INTO permissao (codigo, descricao) values (5, 'ROLE_CADASTRAR_EMPRESA');
INSERT INTO permissao (codigo, descricao) values (6, 'ROLE_REMOVER_EMPRESA');

INSERT INTO permissao (codigo, descricao) values (7, 'ROLE_PESQUISAR_FUNCIONARIO');
INSERT INTO permissao (codigo, descricao) values (8, 'ROLE_CADASTRAR_FUNCIONARIO');
INSERT INTO permissao (codigo, descricao) values (9, 'ROLE_REMOVER_FUNCIONARIO');

INSERT INTO permissao (codigo, descricao) values (10, 'ROLE_PESQUISAR_HORARIO');
INSERT INTO permissao (codigo, descricao) values (11, 'ROLE_CADASTRAR_HORARIO');
INSERT INTO permissao (codigo, descricao) values (12, 'ROLE_REMOVER_HORARIO');

INSERT INTO permissao (codigo, descricao) values (13, 'ROLE_ADMIN');

INSERT INTO permissao (codigo, descricao) values (14, 'ROLE_PESQUISAR_LAUDO');
INSERT INTO permissao (codigo, descricao) values (15, 'ROLE_CADASTRAR_LAUDO');
INSERT INTO permissao (codigo, descricao) values (16, 'ROLE_REMOVER_LAUDO');

INSERT INTO permissao (codigo, descricao) values (17, 'ROLE_GERAR_RELATORIO');
INSERT INTO permissao (codigo, descricao) values (18, 'ROLE_GERAR_LAUDO');

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 13);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (4, 13);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (5, 13);

-- leitura/escrita
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 8);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 11);


-- geral
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 8);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 11);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 12);

-- Somente leitura
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (6, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (6, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (6, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (6, 10);
