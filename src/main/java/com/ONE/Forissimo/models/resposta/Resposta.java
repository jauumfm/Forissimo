package com.ONE.Forissimo.models.resposta;

import com.ONE.Forissimo.models.topico.Topico;
import com.ONE.Forissimo.models.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "resposta")
@Entity(name = "Resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico_id;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private Boolean solucao;
}




