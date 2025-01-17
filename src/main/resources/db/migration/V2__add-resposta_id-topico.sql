ALTER TABLE topico ADD resposta_id BIGINT;
ALTER TABLE topico ADD CONSTRAINT fk_topico_resposta FOREIGN KEY (resposta_id) REFERENCES resposta(id);
