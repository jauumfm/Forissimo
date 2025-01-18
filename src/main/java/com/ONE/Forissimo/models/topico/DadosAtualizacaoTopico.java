package com.ONE.Forissimo.models.topico;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(@NotNull
                                     Long id,
                                     String titulo,
                                     String mensagem) {
}
