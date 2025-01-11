package com.ONE.Forissimo.models.topico;

import com.ONE.Forissimo.models.curso.Curso;
import com.ONE.Forissimo.models.resposta.Resposta;
import com.ONE.Forissimo.models.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topico")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToOne
    @JoinColumn(name = "resposta_id")
    private Resposta resposta; // Mapeia a relação de 1 para 1 com Resposta
}


