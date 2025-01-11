package com.ONE.Forissimo.models.usuario;

import com.ONE.Forissimo.models.perfil.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastro(@NotBlank(message = "{nome.obrigatorio}")
                            String nome,

                            @NotBlank
                            @Email
                            String email,

                            @NotBlank
                            String senha,

                            @NotBlank
                            @NotNull
                            Perfil perfil) {
}
