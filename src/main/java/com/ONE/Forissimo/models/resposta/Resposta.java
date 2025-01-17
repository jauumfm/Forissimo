package com.ONE.Forissimo.models.resposta;

import com.ONE.Forissimo.models.topico.Topico;
import com.ONE.Forissimo.models.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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

    @Column(length = 1000) // Reflete a mudança no banco
    @Size(max = 1000, message = "A mensagem não pode ter mais de 1000 caracteres")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private Boolean solucao;

    public Resposta(@Valid DadosCriacaoResposta dados, Topico topico, Usuario autor) {
        this.autor = autor;
        this.data = LocalDateTime.now();
        this.mensagem = dados.mensagem();
        this.topico = topico;
        this.solucao = false;
    }

    private Resposta(){}

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Topico getTopico() {
        return topico;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Boolean getSolucao() {
        return solucao;
    }

}




