package com.ONE.Forissimo.controller;


import com.ONE.Forissimo.models.curso.CursoRepository;
import com.ONE.Forissimo.models.resposta.*;
import com.ONE.Forissimo.models.topico.DadosCriacaoTopico;
import com.ONE.Forissimo.models.topico.DadosDetalhamentoTopico;
import com.ONE.Forissimo.models.topico.Topico;
import com.ONE.Forissimo.models.topico.TopicoRepository;
import com.ONE.Forissimo.models.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/resposta")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*CRIANDO RESPOSTAS*/
    @PostMapping
    @Transactional
    public ResponseEntity criarResposta(@RequestBody @Valid DadosCriacaoResposta dados, UriComponentsBuilder uriBuilder) {
        /*UriComponentsBuilder CRIA A PRIMEIRA PARTE DO LINK ESCONDENDO O MSM */
        var autor = usuarioRepository.findById(dados.autorId())/*verificando se o autor existe*/
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));
        var topico = topicoRepository.findById(dados.topicoId())/*verificando se o curso existe*/
                .orElseThrow(() -> new IllegalArgumentException("topico não encontrado"));
        var resposta = new Resposta(dados, topico, autor);
        respostaRepository.save(resposta);
        /*ESSE CODIGO RETORNA 201 Q EXIGE TAMBEM O Q FOI CADASTRAO E UM CABECALHO COM URI*/
        var uri = uriBuilder.path("/topico/{topicoId}/resposta/{respostaId}")
                .buildAndExpand(topico.getId(),resposta.getId())
                .toUri();
        /*CAMINHO BASCIO LOCALHOST(COMPLEMENTO DO CAMINHO).PEGA O ID Q  FOI CRIADO AGOPRA.TRANSFORMA EM URI*/
        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    /*ATUALIZNDO RESPOSTA*/
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoResposta dados) {
        var resposta = respostaRepository.getReferenceById(dados.id());
        resposta.atualizarResposta(dados);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    /*EXCLUINDO RESPOSTA*/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        respostaRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
