package com.ONE.Forissimo.models.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastro(@NotBlank(message = "{nome.obrigatorio}")
                            String nome,

                            @NotBlank
                            @Email
                            String email,

                            @NotBlank
                            String senha,

                            Long perfilId) {
}
