package com.ONE.Forissimo.models.topico;

import com.ONE.Forissimo.models.resposta.Resposta;
import com.ONE.Forissimo.models.resposta.RespostaListagem;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCompletosTopico (long id,
                                    String titulo,
                                    String mensagem,
                                    boolean status,
                                    String autor,
                                    LocalDateTime data,
                                    String curso,
                                    String categoria,
                                    List<RespostaListagem> respostas){

    public DadosCompletosTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),topico.getStatus(),
                topico.getAutor().getNome(),topico.getData(),topico.getCurso().getNome(),topico.getCurso().getCategoria().name(),
                topico.getResposta()/*criando lista de RespostasListagem*/
                        .stream()
                        .map(RespostaListagem::new)
                        .toList());
    }
}
