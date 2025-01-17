package com.ONE.Forissimo.models.perfil;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "perfil")
@Entity(name = "Perfil")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoPerfil nome;

    public TipoPerfil getNome() {
        return TipoPerfil.valueOf(String.valueOf(nome));
    }

    public void setNome(String nome) {
        this.nome = TipoPerfil.valueOf(nome);
    }

}
