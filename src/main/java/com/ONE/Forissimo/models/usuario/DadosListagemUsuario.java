package com.ONE.Forissimo.models.usuario;

import com.ONE.Forissimo.models.perfil.Perfil;

public record DadosListagemUsuario(Long id,
                                   String nome,
                                   String email,
                                   Perfil perfil) {

    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil());
    }

}
