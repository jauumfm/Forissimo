package com.ONE.Forissimo.models.topico;

import com.ONE.Forissimo.models.resposta.Resposta;
import com.ONE.Forissimo.models.resposta.RespostaListagem;

import java.time.LocalDateTime;
import java.util.Comparator;
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
                topico.getAutor().getAtivo()?topico.getAutor().getNome():"usuario desativado",topico.getData(),topico.getCurso().getNome(),topico.getCurso().getCategoria().name(),
                topico.getResposta()/*criando lista de RespostasListagem*/
                        .stream()
                        .sorted(Comparator.comparing(Resposta::getSolucao).reversed()) // Ordena por 'solucao' (true primeiro)
                        .map(RespostaListagem::new)
                        .toList());
    }
}
