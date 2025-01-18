package com.ONE.Forissimo.models.resposta;

import com.ONE.Forissimo.models.topico.Topico;
import com.ONE.Forissimo.models.topico.TopicoSimples;
import com.ONE.Forissimo.models.usuario.Usuario;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(Long id,
                                        String mensagem,
                                        boolean solucao,
                                        String autor,
                                        LocalDateTime data,
                                        TopicoSimples topico) {
    public DadosDetalhamentoResposta (Resposta resposta){
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getSolucao(),
                resposta.getAutor().getAtivo()?resposta.getAutor().getNome():"usuario desativado",
                resposta.getData(),
                new TopicoSimples(resposta.getTopico())
        );
    }
}
