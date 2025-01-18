package com.ONE.Forissimo.models.resposta;

import com.ONE.Forissimo.models.topico.Topico;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoResposta(@NotNull
                                     Long id,
                                       String mensagem){
}
