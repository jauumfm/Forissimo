package com.ONE.Forissimo.models.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCurso (
        Long id,
        @NotBlank
        String nome,

        @NotNull
        Categoria categoria) {

        public DadosCurso(Curso curso) {
                this(curso.getId(), curso.getNome(), Categoria.valueOf(curso.getCategoria().name()));
        }
}
