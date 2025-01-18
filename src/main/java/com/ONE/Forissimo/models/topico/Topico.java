package com.ONE.Forissimo.models.topico;

import com.ONE.Forissimo.models.curso.Curso;
import com.ONE.Forissimo.models.resposta.Resposta;
import com.ONE.Forissimo.models.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topico")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Proxy(lazy = false)
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @Column(length = 1000) // Reflete a mudança no banco
    @Size(max = 1000, message = "A mensagem não pode ter mais de 1000 caracteres")
    private String mensagem;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;


    @OneToMany(mappedBy = "topico",fetch = FetchType.LAZY)/*MAPEANDO A RESPOSTA PRA SER VINCULADA AO TOPICO*/
    private List<Resposta> resposta;// Mapeia a relação de 1 para 1 com Resposta

    public Topico(@Valid DadosCriacaoTopico dados, Curso curso, Usuario autor) {
        this.autor = autor;
        this.curso = curso;
        this.titulo = dados.titulo();
        this.data = LocalDateTime.now();
        this.mensagem = dados.mensagem();
        this.status = false;
    }

    public void atualizarTopico(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
    }

    /*LOMBOK COM DEFEITO*/

    public Topico() {
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Boolean getStatus() {
        return status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Resposta> getResposta() {
        return resposta;
    }

    public void respondido(Topico topico) {
        this.status=true;
    }
}


