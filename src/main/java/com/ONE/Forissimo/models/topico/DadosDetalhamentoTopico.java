package com.ONE.Forissimo.models.topico;

import com.ONE.Forissimo.models.curso.Curso;
import com.ONE.Forissimo.models.perfil.Perfil;
import com.ONE.Forissimo.models.usuario.Usuario;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(long id,
                                      String titulo,
                                      String mensagem,
                                      boolean status,
                                      String autor,
                                      LocalDateTime data,
                                      String curso,
                                      String categoria) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),topico.getStatus(),
                topico.getAutor().getNome(),topico.getData(),topico.getCurso().getNome(),topico.getCurso().getCategoria().name());

    }
}
