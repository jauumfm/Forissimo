package com.ONE.Forissimo.models.resposta;

import java.time.LocalDateTime;

public record RespostaListagem(Long id,
                               String mensagem,
                               boolean solucao,
                               String autor,
                               LocalDateTime data) {
    public RespostaListagem(Resposta resposta){
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getSolucao(),
                resposta.getAutor().getAtivo()?resposta.getAutor().getNome():"usuario desativado",
                resposta.getData());
    }
}
