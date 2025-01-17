package com.ONE.Forissimo.models.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCriacaoTopico (
        @NotBlank
        String titulo,

        @NotBlank
        String mensagem,

        @NotNull
        Long autorId,

        @NotNull
        Long cursoId ){
}
