-- Tabela PERFIL
CREATE TABLE perfil (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE, -- Nome do perfil, por exemplo: "Aluno", "Instrutor", "Administrador"
    PRIMARY KEY (id)
);

-- Tabela USUARIO
CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (perfil_id) REFERENCES perfil(id)
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
    mensagem VARCHAR(100) NOT NULL,
    status BOOLEAN NOT NULL,
    autor_id BIGINT NOT NULL,
    data DATETIME NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuario(id),
    CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE resposta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem VARCHAR(100) NOT NULL,
    topico_id BIGINT NOT NULL,
    data DATETIME NOT NULL,
    autor_id BIGINT NOT NULL,
    solucao BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_resposta_topico FOREIGN KEY (topico_id) REFERENCES topico(id),
    CONSTRAINT fk_resposta_autor FOREIGN KEY (autor_id) REFERENCES usuario(id)
);
