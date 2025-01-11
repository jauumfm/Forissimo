-- Tabela PERFIL
CREATE TABLE perfil (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE, -- Nome do perfil, por exemplo: "Aluno", "Instrutor", "Administrador"
    PRIMARY KEY (id)
);

-- Tabela USUARIO
CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela USUARIO_PERFIL (Associação entre USUARIO e PERFIL)
CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_usuario_perfil_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
    CONSTRAINT fk_usuario_perfil_perfil FOREIGN KEY (perfil_id) REFERENCES perfil (id)
);

-- Tabela CURSO
CREATE TABLE curso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela TOPICO
CREATE TABLE topico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    data DATETIME NOT NULL,
    status BOOLEAN NOT NULL DEFAULT FALSE, -- Indica se a dúvida foi sanada
    autor_id BIGINT NOT NULL, -- Referência ao autor (usuário)
    curso_id BIGINT NO
