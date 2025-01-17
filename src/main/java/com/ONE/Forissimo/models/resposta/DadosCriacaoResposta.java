package com.ONE.Forissimo.models.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCriacaoResposta(@NotBlank
                                   String mensagem,

                                   @NotNull
                                   Long topicoId,

                                   @NotNull
                                   Long autorId) {
}
