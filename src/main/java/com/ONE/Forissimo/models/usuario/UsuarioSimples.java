package com.ONE.Forissimo.models.usuario;

import com.ONE.Forissimo.models.perfil.Perfil;

public record UsuarioSimples(
        Long id,
        String nome,
        Perfil perfil
) {
    public UsuarioSimples(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getPerfil());
    }
}


