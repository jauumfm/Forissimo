package com.ONE.Forissimo.models.usuario;

import com.ONE.Forissimo.models.perfil.Perfil;

public record DadosDetalhamento(long id,
                                String nome,
                                String email,
                                Perfil perfil_id,
                                String senha) {
    public DadosDetalhamento (Usuario user) {
        this(user.getId(), user.getNome(), user.getEmail(), user.getPerfil_id(), user.getSenha());

    }
}
