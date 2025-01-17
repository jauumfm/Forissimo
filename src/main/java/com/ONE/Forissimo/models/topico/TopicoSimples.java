package com.ONE.Forissimo.models.topico;

import com.ONE.Forissimo.models.curso.DadosCurso;
import com.ONE.Forissimo.models.usuario.UsuarioSimples;

import java.time.LocalDateTime;

public record TopicoSimples(Long id,
                            String titulo,
                            String mensagem,
                            UsuarioSimples autor,
                            LocalDateTime data,
                            DadosCurso curso) {
    public TopicoSimples(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                new UsuarioSimples(topico.getAutor()),
                topico.getData(),
                new DadosCurso(topico.getCurso())
        );
}
}
