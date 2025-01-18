package com.ONE.Forissimo.controller;


import com.ONE.Forissimo.infra.exception.ValidacaoException;
import com.ONE.Forissimo.models.curso.CursoRepository;
import com.ONE.Forissimo.models.resposta.DadosDetalhamentoResposta;
import com.ONE.Forissimo.models.resposta.Resposta;
import com.ONE.Forissimo.models.resposta.RespostaRepository;
import com.ONE.Forissimo.models.topico.*;
import com.ONE.Forissimo.models.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("topico")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    /*CADASTRANDO TOPICO*/
    @PostMapping
    @Transactional
    public ResponseEntity criarTopico(@RequestBody @Valid DadosCriacaoTopico dados, UriComponentsBuilder uriBuilder) {
        /*UriComponentsBuilder CRIA A PRIMEIRA PARTE DO LINK ESCONDENDO O MSM */
        var autor = usuarioRepository.findById(dados.autorId())/*verificando se o autor existe*/
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));
                if (!autor.getAtivo()){
                    throw new ValidacaoException("Usuario excluido ou desativado");
                }
        var curso = cursoRepository.findById(dados.cursoId())/*verificando se o curso existe*/
                .orElseThrow(() -> new IllegalArgumentException("curso não encontrado"));
        var topico = new Topico(dados, curso,autor);
        topicoRepository.save(topico);
        /*ESSE CODIGO RETORNA 201 Q EXIGE TAMBEM O Q FOI CADASTRAO E UM CABECALHO COM URI*/
        var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        /*CAMINHO BASCIO LOCALHOST(COMPLEMENTO DO CAMINHO).PEGA O ID Q  FOI CRIADO AGOPRA.TRANSFORMA EM URI*/
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    /*LISTANDO TOPICOS*/
    @GetMapping
    public ResponseEntity<Page<TopicoSimples>> listar(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var page = topicoRepository.findAll(paginacao)
                .map(TopicoSimples::new);
        return ResponseEntity.ok(page);/*retorna o 200 mais as infos q estao em page*/
    }

    /*LISTANDO APENAS UM TOPICO*/
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        if (!topico.getAutor().getAtivo()){
            topico.getAutor().setNome("usuario desativado");
        }
        return ResponseEntity.ok(new DadosCompletosTopico(topico));
    }

    /*EDITAR TOPICO*/
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topico = topicoRepository.getReferenceById(dados.id());
        topico.atualizarTopico(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    /*EXCLUINDO topico*/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        respostaRepository.deleteByTopicoId(id);
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /*setando RESPOSTA do ropico*/
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity setResposta(@PathVariable Long id, @RequestBody @Valid Long idResposta) {
        var topico = topicoRepository.getReferenceById(id);
        topico.respondido(topico);
        List<Resposta> respostas = respostaRepository.findByTopico_Id(id);
        respostas.forEach(resp -> resp.setSolucao(false));
        respostaRepository.saveAll(respostas);
        var resposta = respostaRepository.getReferenceById(idResposta);
        resposta.setResposta(id,resposta);
        return ResponseEntity.noContent().build();
    }
}
